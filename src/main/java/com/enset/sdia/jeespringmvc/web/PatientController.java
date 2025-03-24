package com.enset.sdia.jeespringmvc.web;

import com.enset.sdia.jeespringmvc.entity.Patient;
import com.enset.sdia.jeespringmvc.repository.PatientRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private PatientRepo patientRepo;

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "2") int size, @RequestParam(name = "keyword",defaultValue = "") String ky){
        Page<Patient> patients = patientRepo.findByNomContains(ky,PageRequest.of(page,size));
        model.addAttribute("patients", patients);
        model.addAttribute("pages", new int[patients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("ky", ky);
        return "patient";
    }

    @GetMapping("/delete")
    public String delete(Long id,String keyword,int page){
        patientRepo.deleteById(id);
        return "redirect:/patient/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping()
    public String home(){
        return "redirect:/patient/index";
    }

    @GetMapping("/formPatient")
    public String formPatient(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }
    @PostMapping("/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "") String keyword){
        if(bindingResult.hasErrors()){
            return "redirect:/patient/formPatient";
        }
        patientRepo.save(patient);
        return "redirect:/patient/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/editPatient")
    public String editPatient(Model model, Long id,String keyword, int page){
        Patient patient = patientRepo.findById(id).orElseThrow(()->new RuntimeException("Patient not found"));
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatient";
    }
}
