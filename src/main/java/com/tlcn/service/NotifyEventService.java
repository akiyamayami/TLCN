package com.tlcn.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlcn.dao.NotifyEventRepository;
import com.tlcn.dto.ModelShowNotify;
import com.tlcn.model.NotifyEvent;
import com.tlcn.model.Proposal;
import com.tlcn.model.User;

@Service
public class NotifyEventService {
	@Autowired
	private NotifyEventRepository notifyEventRepository;
	
	public NotifyEventService() {
		super();
	}
	
	public List<ModelShowNotify> getListNotifyNewest(User user){
		List<ModelShowNotify> listnotify = new ArrayList<>();
		List<NotifyEvent> x  =  notifyEventRepository.getListNotifyNewest(user);
		for(NotifyEvent notify : x){
			Proposal proposal = notify.getNotifyOfProposal();
			listnotify.add(new ModelShowNotify(notify.getNotifyOfProposal().getProposalID(),notify.getMessage(), getTime(notify.getDateUpEvent())));
			System.out.println(proposal.getUserregister().getUser().getName());
		}
		return listnotify;
	}
	
	public String generateMessageNotify(NotifyEvent notify, boolean isUser,String type){
		boolean isProposalConfirm = (notify.getNotifyOfProposal().getStt().getSttproposalID() == 1);
		boolean typeProposalisCancel = (notify.getNotifyOfProposal().getType().getTypeID() == 3);
		String message = ""; 
		switch(type){
			case "CarWasUsed":
				message += "<div>Xe bạn đăng ký cho đề nghị <strong>"+notify.getNotifyOfProposal().getName()+"</strong>"
						+ " đã được sủ dụng vui lòng đổi xe khác.</div>"
						+ "<p class='time-ago'><i class='fa fa-bell' aria-hidden='true'></i>";
				return message;
			default:
				if(isProposalConfirm && !typeProposalisCancel){
					if(!isUser){
						message += "<div><strong>" + notify.getNotifyOfProposal().getInfoconfirm().getUserconfirm().getRoleUser().getName() + "</strong>"
							+ " đã duyệt đề nghị <strong>"+notify.getNotifyOfProposal().getName() +"</strong>"
							+ " của <strong>"+ notify.getNotifyOfProposal().getUserregister().getUser().getName() + "</strong>"
							+ "</div>";
					}else{
						message += "<div><strong>" + notify.getNotifyOfProposal().getInfoconfirm().getUserconfirm().getRoleUser().getName() + "</strong>"
							+ " đã duyệt đề nghị <strong>"+notify.getNotifyOfProposal().getName() +"</strong>"
							+ " của bạn"
							+ "</div>";
					}
					message += "<p class='time-ago'><i class='fa fa-plus-circle' aria-hidden='true'></i>";
				}else if(notify.getNotifyOfProposal().isExpired() && isProposalConfirm){
					message += "<div>Đề nghị <strong>"+ notify.getNotifyOfProposal().getName() +"</strong>"
							+ " đã bị hủy bởi <strong>Hệ thống</strong> vì thời gian sử dụng đã quá hạn"
							+ "</div>";
					message += "<p class='time-ago'><i class='fa fa-trash' aria-hidden='true'></i>";
				}else{
					message += "<div><strong>"+ notify.getNotifyOfProposal().getUserregister().getUser().getName() +"</strong>"
							+ " đã "+ notify.getNotifyOfProposal().getType().getName() +" một đề nghị </div>";
					message += "<p class='time-ago'><i class='fa " 
							+ getIconOfType(notify.getNotifyOfProposal().getType().getName()) +" ' aria-hidden='true'></i>";
				}
				System.out.println(message);
				return message;
		}
	}
	
	public String getIconOfType(String type){
		switch(type){
			case "Tạo":
				return "fa-plus-circle";
			case "Chỉnh sửa":
				return "fa-pencil-square-o";
			default:
				return "fa-trash";
		}
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
