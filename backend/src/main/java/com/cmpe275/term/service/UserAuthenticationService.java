package com.cmpe275.term.service;

import com.cmpe275.term.entity.Address;
import com.cmpe275.term.entity.Participant;
import com.cmpe275.term.entity.User;
import com.cmpe275.term.model.ParticipantRes;
import com.cmpe275.term.model.UserSpecialResponse;
import com.cmpe275.term.repository.UsersRepository;
//import org.modelmapper.ModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.*;

@Service
public class UserAuthenticationService {

    @Autowired
    private UsersRepository repository;
    //@Autowired
    //private ModelMapper mapper;
    @Autowired
    private ModelMapper modelMapper;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User userSignUp(String name, String email, String password, String description, String street, String city, String state, String zip, String accountType,
                           String screenName, String gender) throws AddressException, MessagingException, IOException,Exception{
        User u = repository.findByEmail(email);
        if(u!=null){
            throw new Exception("Email is already in use");
        }
        u = repository.findByscreenName(screenName);
        if(u!=null){
            throw new Exception("Screen Name is already in use");
        }
        User user=new User();
        user.setName(name);
        user.setEmail(email);
        user.setScreenName(screenName);
        user.setAccountType(accountType);
        String pwd = passwordEncoder.encode(password);
        user.setPassword(pwd);
        if(description!=null) {
            user.setDescription(description);
        }
        if(gender!=null){
            user.setGender(gender);
        }
        Address address=new Address();
        if(street!=null) {
            address.setStreet(street);
        }
        if(city!=null) {
            address.setCity(city);
        }
        if(state!=null) {
            address.setState(state);
        }
        if(zip!=null) {
            address.setZip(zip);
        }
        user.setAddress(address);
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String otp = String.format("%06d", number);
        user.setAccountValidated(0);
        user.setOTP(otp);
        String message="Hi "+user.getName()+". Please use "+otp+" to validate your account. Without validation you cannot login";
        String header = "OTP for account verification";
        sendMailToRecipients(user.getName(),user.getEmail(),message,header);
        User res= repository.save(user);
        res.setOTP(null);
        return res;
    }

    public UserSpecialResponse userSignIn(String email, String password, String authType) throws Exception{
        User user =this.repository.findByEmail(email);
        if(user==null){
            throw new Exception("User not yet registered");
        }
        if(!authType.equals("google") && !passwordEncoder.matches(password,user.getPassword())){
            throw new Exception("Incorrect Credentials");
        }
        if(user.getAccountValidated()==0) {
            throw new Exception("Account not validated yet. Please validate your account!");
        }

        user.setPassword("");
        user.setOTP("");
        List<ParticipantRes> list = new ArrayList<>();
        for(Participant p : user.getParticipants()){
            ParticipantRes pRes = this.modelMapper.map(p,ParticipantRes.class);
            pRes.setEventId(p.getEvent().getId());
            list.add(pRes);
        }
        UserSpecialResponse res = new UserSpecialResponse();
        res.setAddress(user.getAddress());
        res.setAccountType(user.getAccountType());
        res.setDescription(user.getDescription());
        res.setEmail(user.getEmail());
        res.setGender(user.getGender());
        res.setAccountValidated(user.getAccountValidated());
        res.setName(user.getName());
        res.setAverageRating(user.getAverageRating());
        res.setAnswers(user.getAnswers());
        res.setEvents(user.getEvents());
        res.setScreenName(user.getScreenName());
        res.setTotalRatingsReceived(user.getTotalRatingsReceived());
        res.setQuestions(user.getQuestions());
        res.setParticipants(list);
        res.setId(user.getId());
        return res;
    }


    public void sendMailToRecipients(String name,String email,String message,String subject) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("CloudEventCenter@gmail.com", "Cmpe275@CEC");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("CloudEventCenter@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        String messageContent=message;
        msg.setSubject(subject);
        msg.setContent(messageContent, "text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }


    public String validateUserWithOTP(String email, String otp) throws AddressException, MessagingException, IOException,Exception {
        User u = repository.findByEmail(email);
        if(u.getAccountValidated()==1){
            throw new Exception("Account already validated.");
        }
        if(u.getOTP().equals(otp)){
            u.setAccountValidated(1);
            repository.save(u);
            String message="Hi. Your account has been validated. Now you can login and access the content";
            String header = "Account has been validated";
            sendMailToRecipients("",email,message,header);
            return "Account has been validated. Now you can use the account";
        }
        throw new Exception("Invalid OTP. Please try again");

    }
}
