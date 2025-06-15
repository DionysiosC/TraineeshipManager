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

import com.uoi.soft_eng.project.model.Evaluation;
import com.uoi.soft_eng.project.model.Professor;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.services.ProfessorService;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }
    

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }


    @RequestMapping("/professor/dashboard")
    public String getProfessorDashboard() {
        return "professor/dashboard";
    }


    @RequestMapping("/professor/profile")
    public String retrieveProfile(Model model) {
        Professor retrievedProfessor = professorService.retrieveProfile(getCurrentUsername());
        model.addAttribute("professor", retrievedProfessor);        
        return "professor/profile";
    }

    @RequestMapping("/professor/save")
    public String saveProfile(@ModelAttribute("professor") Professor professor, Model model) {
        professorService.saveProfile(professor);
        model.addAttribute("successMessage", "Profile updated successfully!");
        return "professor/dashboard";
    }

    @RequestMapping("/professor/positions")
    public String listAssignedTraineeships(Professor professor, Model model) {
        List <TraineeshipPosition> positions = professorService.retrieveAssignedPositions(getCurrentUsername());
        model.addAttribute("positions", positions);  
        return "professor/positions";
    }


    @RequestMapping("/professor/evaluate-position")
    public String evaluateAssignedTraineeship(@RequestParam("positionId")Integer positionId,  Model model) {
        model.addAttribute("positionId", positionId);
        model.addAttribute("evaluation", new Evaluation());
        return "professor/evaluate-position";
    }


    @RequestMapping("/professor/save-evaluation")
    public String saveEvaluation(   @ModelAttribute("positionId")int positionId,
                                    @ModelAttribute("evaluation") Evaluation evaluation,
                                    Model model){
        professorService.saveEvaluation(positionId, evaluation);
        model.addAttribute("successMessage", "Evaluation saved successfully!");
        return "professor/dashboard";
    }

}
