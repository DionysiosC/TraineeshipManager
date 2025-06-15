package com.uoi.soft_eng.project.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uoi.soft_eng.project.model.Evaluation;
import com.uoi.soft_eng.project.model.EvaluationType;
import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.services.CommitteeService;

@Controller
public class CommitteeController {
    @Autowired
    private CommitteeService committeeService;

    @Autowired
    public CommitteeController( CommitteeService committeeService) {
        this.committeeService = committeeService;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }

    @RequestMapping("/committee/dashboard")
    public String getCommitteeDashboard() {
        return "committee/dashboard";
    }
    
    @RequestMapping("/committee/candidates")
    public String getCandidates(Model model) {
        List<Student> candidates = committeeService.retrieveTraineeshipApplications();
        model.addAttribute("candidates", candidates);
        return "committee/candidates";
    } 
    
    @RequestMapping("/committee/progress")
    public String getAssignedTraineeships(Model model) {
        List<TraineeshipPosition> positions = committeeService.listAssignedTraineeships();
        model.addAttribute("positions", positions);
        return "committee/assigned-positions";
    }
    
    @RequestMapping("/committee/search-position")
    public String retrievePositionsBasedOnStrategy(@RequestParam("applicantUsername") String applicantUsername,
                                                   @RequestParam("strategy") String strategy,
                                                   Model model){
        List<TraineeshipPosition> positions = committeeService.retrievePositionsForApplicant(applicantUsername, strategy);
        model.addAttribute("positions", positions);
        model.addAttribute("applicantUsername", applicantUsername);
        return "committee/possible-positions";
    }
    
    @RequestMapping("/committee/assign-student")
    public String assignStudent(    @RequestParam("positionId") Integer positionId,
                                    @RequestParam("applicantUsername") String candidateUsername,
                                    Model model){
        committeeService.assignPosition(positionId, candidateUsername);
        model.addAttribute("message", "Traineeship position assigned successfully!");
        return "committee/dashboard";
    }
    
    @RequestMapping("/committee/assign-supervisor")
    public String assignProfessor(  @RequestParam("positionId") Integer positionId,
                                    @RequestParam("strategy") String strategy,
                                    Model model){
        try{
            committeeService.assignSupervisor(positionId, strategy);
        }catch (IllegalStateException e) {
            model.addAttribute("message", "Supervisor not assigned");
            return "committee/dashboard";
        }
        model.addAttribute("message", "Supervisor assigned successfully!");
        return "committee/dashboard";
    }
    
    @RequestMapping("/committee/finalize-position")
    public String getFinalizePositionForm(  @RequestParam("positionId") Integer positionId,
                                            Model model){
        List<TraineeshipPosition> assignedTraineeships = committeeService.listAssignedTraineeships();
        Iterator<TraineeshipPosition> iterator = assignedTraineeships.iterator();
        TraineeshipPosition position = null;
        Evaluation professorEval = null;
        Evaluation companyEval = null;
        
        while (iterator.hasNext()) {
            position = iterator.next();
            if (position.getId() == positionId) {
                break;
            }
        }
        
        for (Evaluation eval : position.getEvaluations()) {
            if (eval.getEvaluationType() == EvaluationType.PROFESSOR) {
                professorEval = eval;
            } else {
                companyEval = eval;
            }
        }
        
        model.addAttribute("professorEvaluation", professorEval != null ? professorEval:"No evaluation");
        model.addAttribute("companyEvaluation", companyEval != null ? companyEval:"No evaluation");

        model.addAttribute("studentLogbook", position.getStudentLogbook()!= null ?
                                                                    position.getStudentLogbook():"No logbook entries");        
        model.addAttribute("positionId", positionId);
        
        return "committee/finalize-position";
    }
    
    @RequestMapping("/committee/complete")
    public String complete( @RequestParam("positionId") Integer positionId,
                            @RequestParam("finalScore") Double finalScore,
                            Model model){
        
        committeeService.completeAssignedTraineeships(positionId, finalScore);
        model.addAttribute("message", "Traineeship position completed successfully!");
        return "committee/dashboard";
    }
}
