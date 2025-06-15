package com.uoi.soft_eng.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uoi.soft_eng.project.model.Company;
import com.uoi.soft_eng.project.model.EvaluationType;
import com.uoi.soft_eng.project.model.Evaluation;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.services.CompanyService;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }

    @RequestMapping("/company/dashboard")
    public String getCompanyDashboard() {
        return "company/dashboard";
    }

    @RequestMapping("/company/profile")
    public String retrieveProfile(@ModelAttribute("company") Company company, Model model) {
        Company retrievedCompany = companyService.retrieveProfile(getCurrentUsername());
        model.addAttribute("company", retrievedCompany);
        return "company/profile";
    }

    @RequestMapping("/company/save")
    public String saveProfile(@ModelAttribute("company") Company company, Model model) {
        companyService.saveProfile(company);
        model.addAttribute("successMessage", "Profile updated successfully!");
        return "company/dashboard";
    }
    
    @RequestMapping("/company/available-positions")
    public String getAvailablePostitions(Model model) {
        List<TraineeshipPosition> positions = companyService.retrieveAvailablePositions(getCurrentUsername());
        System.out.println("Available positions: " + positions);
        model.addAttribute("positions", positions);
        return "company/available-positions";
    }
    
    @RequestMapping("/company/assigned-positions")
    public String getAssignedPostitions(Model model) {
        List<TraineeshipPosition> positions = companyService.retrieveAssignedPositions(getCurrentUsername());
        System.out.println("Assigned positions: " + positions);
        model.addAttribute("positions", positions);
        return "company/assigned-positions";
    }   
    
    @RequestMapping("/company/add-position")
    public String addPosition(Model model){
        model.addAttribute("traineeshipPosition", new TraineeshipPosition());
        return "company/new-position";
    }
    
    @RequestMapping("/company/delete-position")
    public String deletePosition(@RequestParam("positionId")int positionId,  Model model){
        companyService.deletePosition(positionId);
        model.addAttribute("successMessage", "Traineeship position deleted successfully!");
        return "company/dashboard";
    }
    
    @RequestMapping("/company/save-position")
    public String savePosition(@ModelAttribute("traineeshipPosition") TraineeshipPosition traineeshipPosition,  Model model){
        companyService.addPosition(getCurrentUsername(), traineeshipPosition);
        model.addAttribute("successMessage", "Traineeship position saved successfully!");
        return "company/dashboard";
    }
    
    @RequestMapping("/company/evaluate-position")
    public String evaluatePosition(@RequestParam("positionId")Integer positionId,  Model model){
        model.addAttribute("positionId", positionId);
        model.addAttribute("evaluation", new Evaluation());
        return "company/evaluate-position";
    }
    
    @RequestMapping("/company/save-evaluation")
    public String saveEvaluation(   @ModelAttribute("positionId")int positionId,
                                    @ModelAttribute("evaluation") Evaluation evaluation,
                                    Model model){
        companyService.saveEvaluation(positionId, evaluation);
        model.addAttribute("successMessage", "Evaluation saved successfully!");
        return "company/dashboard";
    }
    
}
