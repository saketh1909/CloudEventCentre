package com.cmpe275.term.service;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.Review;
import com.cmpe275.term.entity.User;
import com.cmpe275.term.model.ReviewResponse;
import com.cmpe275.term.model.UsersRes;
import com.cmpe275.term.repository.EventRepository;
import com.cmpe275.term.repository.ReviewRepository;
import com.cmpe275.term.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    public ReviewResponse createReview(Long reviewerId, Long revieweeId, Long eventId, int rating, String reviewComment) throws Exception {
        Optional<User> reviewer = usersRepository.findById(reviewerId);
        Optional<User> reviewee = usersRepository.findById(revieweeId);
        Optional<Event> e = eventRepository.findById(eventId);
        if(!reviewer.isPresent()){
            throw new Exception("Invalid Reviewer Id");
        }
        if(!reviewee.isPresent()){
            throw new Exception("Invalid Reviewee Id");
        }
        if(!e.isPresent()){
            throw new Exception("Invalid Event Id");
        }
        Review review = new Review();
        review.setRating(rating);
        review.setReviewText(reviewComment);
        review.setReviewerId(reviewerId);
        review.setRevieweeId(revieweeId);
        review.setEvent(e.get());
        Review r =repository.save(review);
        ReviewResponse res=new ReviewResponse();
        reviewer.get().setPassword("");
        reviewee.get().setPassword("");
        UsersRes r1=this.modelMapper.map(reviewer.get(),UsersRes.class);
        UsersRes r2=this.modelMapper.map(reviewee.get(),UsersRes.class);
        res.setReviewer(r1);
        res.setReviewee(r2);
        res.setEvent(e.get());
        res.setRating(rating);
        res.setId(r.getId());
        res.setReviewComment(r.getReviewText());
        double averageRating  = reviewee.get().getAverageRating();
        double totalratings = reviewee.get().getTotalRatingsReceived();
        averageRating*=totalratings;
        averageRating = (averageRating + (double)rating)/(totalratings+1.0);
        reviewee.get().setAverageRating(averageRating);
        reviewee.get().setTotalRatingsReceived((int)totalratings+1);
        usersRepository.save(reviewee.get());
        String message="Hi. You have a received a review. Please check it out in the website";
        String header="Review Received";
        emailService.sendMailToRecipients("",reviewee.get().getEmail(),message,header);
        return res;
    }

    public List<ReviewResponse> getUserReviewsForUser(Long userId) {
        List<Review> reviews = repository.findByrevieweeId(userId);
        List<ReviewResponse> res=new ArrayList<>();
        for(Review r : reviews){
            ReviewResponse response=this.modelMapper.map(r,ReviewResponse.class);
            response.setEvent(r.getEvent());
            User reviewer = usersRepository.findById(r.getReviewerId());
            User reviewee = usersRepository.findById(r.getRevieweeId());
            UsersRes r1=this.modelMapper.map(reviewer,UsersRes.class);
            UsersRes r2=this.modelMapper.map(reviewee,UsersRes.class);
            response.setReviewer(r1);
            response.setReviewee(r2);
            response.setReviewComment(r.getReviewText());
            res.add(response);
        }
        return res;
    }

    public List<ReviewResponse> getUserReviewsByUser(Long userId) {
        List<Review> reviews = repository.findByreviewerId(userId);
        List<ReviewResponse> res=new ArrayList<>();
        for(Review r : reviews){
            ReviewResponse response=this.modelMapper.map(r,ReviewResponse.class);
            response.setEvent(r.getEvent());
            User reviewer = usersRepository.findById(r.getReviewerId());
            User reviewee = usersRepository.findById(r.getRevieweeId());
            UsersRes r1=this.modelMapper.map(reviewer,UsersRes.class);
            UsersRes r2=this.modelMapper.map(reviewee,UsersRes.class);
            response.setReviewer(r1);
            response.setReviewee(r2);
            response.setReviewComment(r.getReviewText());
            res.add(response);
        }
        return res;
    }
}
