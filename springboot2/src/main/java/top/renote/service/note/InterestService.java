package top.renote.service.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.renote.daoMapper.InterestMapper;
import top.renote.daoMapper.RatingMapper;
import top.renote.model.Interest;
import top.renote.model.Rating;

import java.util.Date;
import java.util.List;

/**
 * Created by Joder_X on 2018/2/26.
 */
@Service
public class InterestService {

    @Autowired
    private InterestMapper interestMapper;
    @Autowired
    private RatingMapper ratingMapper;

    @Transactional
    public boolean insertOne(Interest interest){
        Interest interest1 = interestMapper.getByUIdAndNId(interest);
        int i = 0;
        if (interest1==null){
            i = interestMapper.insertOne(interest);
            addRating(interest);
        }else {
            i = interestMapper.updateTypes(interest);
            addRating(interest);
        }
        return i>0;
    }

    private boolean addRating(Interest interest){
        Rating rating = ratingMapper.getOne(new Rating(null,null,interest.getUser().getUserId(),interest.getNote().getNoteId()));
        Double score = interest.getTypes()==0?1:rating==null?3:rating.getPreference()+1;
        score = Math.min(5,score);
        if (rating==null){
            rating = new Rating(score,new Date().getTime(),interest.getUser().getUserId(),interest.getNote().getNoteId());
            return ratingMapper.insertOne(rating)>0;
        }else {
            rating.setPreference(score);
            return ratingMapper.updatePreference(rating)>0;
        }
    }

    public boolean isLike(Long userId,Long noteId){
        return interestMapper.isLike(userId,noteId)>0;
    }

}
