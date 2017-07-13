package com.tlcn.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlcn.dao.NotifyEventRepository;
import com.tlcn.model.ModelShowNotify;
import com.tlcn.model.NotifyEvent;
import com.tlcn.model.User;

@Service
public class NotifyEventService {
	@Autowired
	private NotifyEventRepository notifyEventRepository;
	@Autowired
	private ProposalService propsalService;
	
	public NotifyEventService() {
		super();
	}
	
	public List<ModelShowNotify> getListNotifyNewest(User user){
		List<ModelShowNotify> listnotify = new ArrayList<>();
		List<NotifyEvent> x  =  notifyEventRepository.getListNotifyNewest(user);
		for(NotifyEvent notify : x){
			listnotify.add(new ModelShowNotify(notify.getNotifyOfUser(), getTime(notify.getDateUpEvent()), notify.getTypeProposal(), propsalService.findOne(notify.getProposalID()), notify.getStt()));
		}
		return listnotify;
	}
	public String getTime(Calendar x){
		Calendar now = Calendar.getInstance();
		long timenow = now.getTime().getTime();
		long time = x.getTime().getTime();
		long day = (timenow - time ) / (24 * 3600 * 1000);
		long hours = (timenow - time - day * (24 * 3600 * 1000)) / (3600 * 1000);
		long min = (timenow - time - day * (24 * 3600 * 1000) - hours * (3600 * 1000)) / (60 * 1000);
		if(day == 0){
			if(hours == 0){
				if(min == 0){
					return "Vừa xong";
				}
				else{
					return min + " phút trước";
				}
			}
			else{
				return hours + " giờ trước";
			}
		}
		else if(day == 1){
			return "Hôm qua lúc " + x.get(Calendar.HOUR_OF_DAY) + ":" + x.get(Calendar.MINUTE);
		}
		else{
			return x.get(Calendar.DATE) + " tháng " + x.get(Calendar.MONTH) + " lúc "+ x.get(Calendar.HOUR_OF_DAY) + ":" + x.get(Calendar.MINUTE);
		}
	}
	public void save(NotifyEvent notify){
		notifyEventRepository.save(notify);
	}
	
	
}
