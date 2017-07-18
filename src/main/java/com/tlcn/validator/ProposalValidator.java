package com.tlcn.validator;

import java.util.Calendar;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tlcn.model.ModelCreateorChangeProposal;

@Component
public class ProposalValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		/*ModelCreateorChangeProposal x = (ModelCreateorChangeProposal) target;
		Calendar now = Calendar.getInstance();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.Proposal.tieude");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "detail", "NotEmpty.Proposal.tieude");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typedateuse", "NotEmpty.Proposal.tieude");
		if(x.getTypedateuse() == "manyday"){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usefromdate", "NotEmpty.Proposal.usefromdate");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usetodate", "NotEmpty.Proposal.usefromdate");
			if(x.getUsefromdate() != null && x.getUsetodate() != null && 
					(x.getUsefromdate().getTime() <  now.getTime().getTime() || 
							x.getUsetodate().getTime() <  now.getTime().getTime()) || 
					x.getUsetodate().getTime() < x.getUsefromdate().getTime())
				errors.rejectValue("usefromdate", "WrongDate.Proposal.usedate");
		}
		if(x.getTypedateuse() == "inday"){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "useindate", "NotEmpty.Proposal.useindate");
			if(x.getUseindate() != null && x.getUseindate().getTime() <  now.getTime().getTime())
				errors.rejectValue("useindate", "DateLow.Proposal.useindate");
		}*/
	}

}
