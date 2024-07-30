package pt.ulisboa.tecnico.rnl.dei.dms.candidates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
    
    @Autowired
    private CandidateService candidateService;

    @GetMapping(value = "/all",
                produces = "application/json; charset=utf-8")
    public List<CandidateDto>
    getCandidates() {
      return candidateService.getCandidates();
    }

    @PostMapping("/create")
    public CandidateDto createCandidate(@RequestBody CandidateDto candidateDto) {
        return candidateService.createCandidate(candidateDto);
    }

    @PutMapping("/update")
    public List<CandidateDto> updateCandidate(@RequestBody CandidateDto candidateDto) {
        return candidateService.updateCandidate(candidateDto);
    }

    @DeleteMapping("/delete/{id}")
    public List<CandidateDto> deleteCandidate(@PathVariable Long id) {
        return candidateService.deleteCandidate(id);
    }
}
