package com.cmpe275.term.repository;

import com.cmpe275.term.entity.Event;
import com.cmpe275.term.entity.Event_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventSearchRepository implements EventSearchRepositoryCustom    {

    @PersistenceContext
    private EntityManager em;



    public List<Event> findAllEventsByPredicates(String location, String status,String startTime,String endTime,String keyword,String organizer){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> event = cq.from(Event.class);
        event.join(Event_.user);
        cq.select(event).distinct(true);
        List<Predicate> predicates = new ArrayList<>();
        if(location!=null && location.length()>0 && !location.isBlank() && !location.isEmpty()){
            Predicate locationPredicate = cb.equal(event.get("address").get("city"), location);
            predicates.add(locationPredicate);
        }
        if(status!=null && status.length()>0 && !status.isBlank()){
            Predicate statusPredicate;
            switch (status){
                case "openForRegistrations":
                    statusPredicate = cb.equal(event.get("status"), 0);
                    predicates.add(statusPredicate);
                    break;
                case "active":
                    statusPredicate = cb.equal(event.get("status"), 2);
                    predicates.add(statusPredicate);
                    break;
            }

        }
        Predicate timepredicate;
        if(endTime.length()==0){
            timepredicate=cb.greaterThanOrEqualTo(event.get("start_date"),java.sql.Timestamp.valueOf(startTime));
            predicates.add(timepredicate);
        }else{
            timepredicate=cb.and(cb.greaterThanOrEqualTo(event.get("start_date"),java.sql.Timestamp.valueOf(startTime)),cb.lessThanOrEqualTo(event.get("end_date"),java.sql.Timestamp.valueOf(endTime)));
            predicates.add(timepredicate);
        }
        Predicate keywordPredicate;
        if(keyword!=null && keyword.length()>0 && !keyword.isBlank() && !keyword.isEmpty()){
            keywordPredicate=cb.or(cb.like(event.get("title"),"%"+keyword+"%"),cb.like(event.get("description"),"%"+keyword+"%"));
            predicates.add(keywordPredicate);
        }
        Predicate organiserPredicate;
        if(organizer!=null && organizer.length()>0 && !organizer.isEmpty()){
            organiserPredicate=cb.like(event.get("user").get("screenName"),"%"+organizer+"%");
            predicates.add(organiserPredicate);
        }
        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Event> query = em.createQuery(cq);
       List<Event> list = query.getResultList();

        return list;
    }
}
