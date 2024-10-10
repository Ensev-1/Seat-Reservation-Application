package com.example.transportationapplication.service;

import com.example.transportationapplication.DTO.UserRegisteredDTO;
import com.lowagie.text.DocumentException;
import com.example.transportationapplication.DTO.BookingsDTO;
import com.example.transportationapplication.model.Bookings;
import com.example.transportationapplication.model.Role;
import com.example.transportationapplication.model.User;
import com.example.transportationapplication.repository.BookingsRepository;
import com.example.transportationapplication.repository.RoleRepository;
import com.example.transportationapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultUserServiceImpl implements DefaultUserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BookingsRepository bookingRepository;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private TemplateEngine templateEngine;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList());
	}

	@Override
	public void save(UserRegisteredDTO userRegisteredDTO) {
		if (userRepo.findByEmail(userRegisteredDTO.getEmail()) != null) {
			throw new IllegalArgumentException("User with this email already exists");
		}

		Role role = roleRepo.findByRole("USER");

		User user = new User();
		user.setEmail(userRegisteredDTO.getEmail());
		user.setName(userRegisteredDTO.getName());
		user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
		user.setRoles(Collections.singleton(role));

		userRepo.save(user);
	}

	@Override
	public Bookings updateBookings(BookingsDTO bookingDTO, UserDetails user) {
		Bookings booking = new Bookings();
		User users = userRepo.findByEmail(user.getUsername());

		booking.setBusId(bookingDTO.getBusId());
		booking.setReservationDate(bookingDTO.getReservationDate());
		booking.setFromDestination(bookingDTO.getFromDestination());
		booking.setToDestination(bookingDTO.getToDestination());
		booking.setNoOfPersons(bookingDTO.getNoOfPersons());
		booking.setTotalCalculated(bookingDTO.getTotalCalculated());
		booking.setTime(bookingDTO.getTime());
		booking.setReservationId(bookingDTO.getReservationId());
		booking.setUserId(users.getId());
		booking.setTripStatus(true);

		String filename = generatePDFAndSendMail(bookingDTO, users);
		booking.setFileName(filename);

		System.out.println("Booking Reservation Id: " + bookingDTO.getReservationId());

		return bookingRepository.save(booking);
	}


	private String generatePDFAndSendMail(BookingsDTO bookingDTO, User users) {
		String filename = users.getName() + "_ticket_" + (int) (Math.random() * 1000 + 1) + ".pdf";

		try {
			createPdf(bookingDTO, users, filename);
			sendEmail(bookingDTO, users, filename);
			return filename;
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	@Override
	public void sendEmail(BookingsDTO bookingDTO, User user, String filename) {
		try {
			final String username = "busio@gmail.com";
			final String password = "busioBestPassword";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject("Booking Confirmation");

			// Create the message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Please find your booking confirmation attached.");

			// Create multipart message
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Add PDF attachment
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Set the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("Failed to send email: " + e.getMessage());
		}

	}


	public void createPdf(BookingsDTO booking, User user, String filename) throws DocumentException, IOException {
		Context context = new Context();

		context.setVariable("name", user.getName());
		context.setVariable("date", booking.getReservationDate());
		context.setVariable("noOfPass", booking.getNoOfPersons());
		context.setVariable("From", booking.getFromDestination());
		context.setVariable("to", booking.getToDestination());
		context.setVariable("busId", booking.getBusId());

		String htmlContent = templateEngine.process("template", context);

		try (OutputStream out = new FileOutputStream(filename)) {
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			renderer.createPDF(out);
		}

	}

}
