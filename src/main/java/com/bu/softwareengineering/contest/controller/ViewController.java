package com.bu.softwareengineering.contest.controller;


import com.bu.softwareengineering.contest.domain.Contest;
import com.bu.softwareengineering.contest.repository.ContestListProjection;
import com.bu.softwareengineering.contest.repository.ContestRepository;
import com.bu.softwareengineering.contest.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    ContestService contestService;

    @Autowired
    ContestRepository contestRepository;

    @RequestMapping("/contest-view")
    public String contestView(Model model){
        List<Contest> contests = contestRepository.findAll();
        model.addAttribute("allContest",contests);
        return "contest";
    }
}
