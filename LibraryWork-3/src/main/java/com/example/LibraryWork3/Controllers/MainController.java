package com.example.LibraryWork3.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
@Controller()
public class MainController {
    List<Program>programs = new ArrayList<>();
     int numberOfProgramsWithException=0;
     int numberOfProgramsDeveloped=0;
    @GetMapping("")
    public String getInitialPage(){
        return "index";
    }

    @GetMapping("/tasked1")
    public String getTask1(Model model){
        model.addAttribute("numberOfInputParameters", "");
        model.addAttribute("numberOfOutputParameters", "");
        model.addAttribute("numberLevelOfLang", "");
        return "tasked1";
    }
    @PostMapping("/tasked1")
    public String calculateTask1(
            @RequestParam Double numberOfInputParameters,
            @RequestParam Double numberLevelOfLang,
            @RequestParam Double numberOfOutputParameters,
            Model model){
        double inputParams = numberOfInputParameters;
        double outputParams = numberOfOutputParameters;
        double numberLevel = numberLevelOfLang;
        double n = inputParams+outputParams;
        model.addAttribute("n", n);
        double V = (n+2)*log2((n+2));
        model.addAttribute("V", String.format("%.3f",V));
        double B = Math.pow(V,2)/(3000*numberLevel);
        model.addAttribute("B",String.format("%.3f",B));
        return "tasked1Final";
    }
    @GetMapping("/error")
    public String customError() {
        return "The link you followed may be broken, or the page may have been removed.";
    }



    @GetMapping("/tasked2")
    public String getTask2Page(Model model){
        model.addAttribute("numberOfInputParameters", "");
        model.addAttribute("numberOfOutputParameters", "");
        model.addAttribute("numberLevelOfLang", "");
        model.addAttribute("numberOfDevelopers", "");
        model.addAttribute("developerProductivity", "");
        return "tasked2";
    }
    @PostMapping("/tasked2")
    public String calculateTask2(
            @RequestParam String numberOfInputParameters,
            @RequestParam String numberOfOutputParameters,
            @RequestParam String numberOfDevelopers,
            @RequestParam String developerProductivity,
            Model model
    ){
        int n = Integer.parseInt(numberOfInputParameters)+Integer.parseInt(numberOfOutputParameters);
        int m = Integer.parseInt(numberOfDevelopers);
        int v = Integer.parseInt(developerProductivity);
        double k = n/8;
        double i=0;
        double N=0;
        double K=0;
        double V=0;
        double P=0;
        double T_k=0;
        double B=0;
        double t_n=0;
        if(k>8){
        i = (log2(n)/3) +1;
        K=n/8+n/64;}
        else{
            K=k;
            i=1;
        }
        N = 220*K+K*log2(K);
        V=K*220*log2(48);
        P=3*N/8;
        T_k=3*N/(8*m*v);
        B = V/3000;
        T_k*=8;
        t_n = T_k/(2*ln(B));
        model.addAttribute("i",String.format("%.3f",i));
        model.addAttribute("N",String.format("%.3f",N));
        model.addAttribute("K",String.format("%.3f",K));
        model.addAttribute("V", String.format("%.3f",V));
        model.addAttribute("P", String.format("%.3f",P));
        model.addAttribute("T_k", String.format("%.3f",T_k));
        model.addAttribute("B", String.format("%.3f",B));
        model.addAttribute("t_n", String.format("%.3f",t_n));
        return "tasked2Final";
    }
    @GetMapping("/tasked3")
    public String getTask3Page(Model model){
        model.addAttribute("numberOfProgramsWithException", "");
        model.addAttribute("numberOfProgramsDeveloped", "");
        return "tasked3";
    }
    @PostMapping("/tasked3")
    public String buildingDataTask3(
            @RequestParam Integer numberOfProgramsWithException,
            @RequestParam Integer numberOfProgramsDeveloped,
            Model model
    ){
        this.numberOfProgramsDeveloped=numberOfProgramsDeveloped;
        this.numberOfProgramsWithException = numberOfProgramsWithException;
        this.programs = new ArrayList<>();
        int j=0;
        return "redirect:tasked3AddProgram";

    }
    @GetMapping("/tasked3AddProgram")
    public String addProgramForTasked3(Model model)
    {
        model.addAttribute("numberOfProgram", this.programs.size());
        model.addAttribute("scopeOfTheProgram", 0);
        model.addAttribute("numberOfErrors",0 );
        return "tasked3AddProgram";
    }
    @PostMapping("tasked3AddProgram")
    public String addProgram(
            @RequestParam Integer numberOfProgram,
            @RequestParam Double scopeOfTheProgram,
            @RequestParam Integer numberOfErrors,
            Model model){
        Program program = new Program(numberOfProgram);
        program.setScopeOfTheProgram(scopeOfTheProgram);
        program.setNumberOfErrors(numberOfErrors);
        this.programs.add(program);
        if(this.numberOfProgramsDeveloped<=this.programs.size()){
            model.addAttribute("scopeOfFutureProgram","");
            model.addAttribute("programs",programs);
            model.addAttribute("numberOfProgramsWithException",numberOfProgramsWithException);
            model.addAttribute("numberOfProgramsDeveloped",numberOfProgramsDeveloped);
            model.addAttribute("numberLevelOfLang","");
            return "tasked3Building";
        }
        else{
            model.addAttribute("numberOfProgram", this.programs.size());
            model.addAttribute("scopeOfTheProgram", 0);
            model.addAttribute("numberOfErrors",0 );
            return "tasked3AddProgram";
        }

    }

    @PostMapping("tasked3Building")
    public String getTasked3Building(
            @RequestParam String scopeOfFutureProgram,
            @RequestParam String numberLevelOfLang,
            Model model
    ){

        double scope = Integer.parseInt(scopeOfFutureProgram);
        model = getRating(new ArrayList<>(programs),Double.parseDouble(numberLevelOfLang),scope,model);
        return "tasked3ShowResults";
    }
    public static double ln(double x){
        double result = Math.log(x);
        return result;
    }
    public static double log2(double x) {
        return (Math.log(x)/Math.log(2));
    }

    public Model getRating(ArrayList<Program>programs,double numberLevelOfLang, double scopeFutureProgram, Model model ){
        double result = 0;
        double R1 = 1000;
        double R2 = 1000;
        double R3 = 1000;
        double B1 =0;
        double B2 =0;
        double B3 =0;
        double sumV=0;
        double c_l_k_L_k1=0;
        double c_l_k_L_k2=0;
        double c_l_k_L_k3=0;
        double sumB_k_c1 = 0;
        double sumB_k_c2 = 0;
        double sumB_k_c3 = 0;
        for(int i=0;i<programs.size();i++){
            sumV+=programs.get(i).scopeOfTheProgram;
            c_l_k_L_k1=1/(numberLevelOfLang+R1);
            c_l_k_L_k2=1/(numberLevelOfLang*R1);
            c_l_k_L_k3=1/numberLevelOfLang+1/R1;
            sumB_k_c1+=programs.get(i).numberOfErrors/(1/(numberLevelOfLang+R1));
            sumB_k_c2+=programs.get(i).numberOfErrors/(1/(numberLevelOfLang*R2));
            sumB_k_c3+=programs.get(i).numberOfErrors/(1/numberLevelOfLang+1/R3);
            R1 = R1 * 1+0.001*(sumV-sumB_k_c1);
            R2 = R2 * 1+0.001*(sumV-sumB_k_c2);
            R3 = R3 * 1+0.001*(sumV-sumB_k_c3);
        }
        B1 = c_l_k_L_k1*scopeFutureProgram;
        B2 = c_l_k_L_k2*scopeFutureProgram;
        B3 = c_l_k_L_k3*scopeFutureProgram;
        model.addAttribute("R1",R1);
        model.addAttribute("R2",R2);
        model.addAttribute("R3",R3);
        model.addAttribute("B1",B1);
        model.addAttribute("B2",B2);
        model.addAttribute("B3",B3);
        model.addAttribute("sumB_k_c1",sumB_k_c1);
        model.addAttribute("sumB_k_c2",sumB_k_c2);
        model.addAttribute("sumB_k_c3",sumB_k_c3);
        return model;
    }
}
class Program{
    Program(int numberOfProgram){
        this.numberOfProgram = numberOfProgram;
    }
    int numberOfProgram;
    double scopeOfTheProgram;
    int numberOfErrors;

    public int getNumberOfProgram() {
        return numberOfProgram;
    }

    public void setNumberOfProgram(int numberOfProgram) {
        this.numberOfProgram = numberOfProgram;
    }

    public double getScopeOfTheProgram() {
        return scopeOfTheProgram;
    }

    public void setScopeOfTheProgram(double scopeOfTheProgram) {
        this.scopeOfTheProgram = scopeOfTheProgram;
    }

    public int getNumberOfErrors() {
        return numberOfErrors;
    }

    public void setNumberOfErrors(int numberOfErrors) {
        this.numberOfErrors = numberOfErrors;
    }
}

