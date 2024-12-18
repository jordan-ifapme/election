package be.ifapme.election.controller.view;

import be.ifapme.election.Exception.BusinessException;
import be.ifapme.election.command.CreateElectionCommand;
import be.ifapme.election.dto.ElectionDto;
import be.ifapme.election.service.ElectionService;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class ElectionViewController {

    private final ElectionService electionService;

    public ElectionViewController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @GetMapping("/view/election")
    public String election(Model model) {
        model.addAttribute("createElectionCommand", new CreateElectionCommand());
        return "election";
    }


    @PostMapping("/view/election")
    public String createElection(Model model, CreateElectionCommand createElectionCommand) throws BusinessException, DocumentException, IOException {
        ElectionDto electionDto = electionService.create(createElectionCommand);
//        model.addAttribute("createElectionCommand", new CreateElectionCommand());
        model.addAttribute("electionDto", electionDto);
        return "election";
    }
}
