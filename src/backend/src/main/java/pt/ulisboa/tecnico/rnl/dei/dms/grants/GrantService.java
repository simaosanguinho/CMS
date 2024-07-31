package pt.ulisboa.tecnico.rnl.dei.dms.grants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.dto.GrantDto;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.repository.GrantRepository;

@Service
public class GrantService {

    @Autowired
    private GrantRepository grantRepository;

    public GrantDto createGrant(GrantDto grantDto) {
        Grant grant = new Grant(grantDto);
        grantRepository.save(grant);
        return new GrantDto(grant);
    }

    public List<GrantDto> getGrants() {
        return grantRepository.findAll().stream().map(GrantDto::new).collect(Collectors.toList());
    }

    public List<GrantDto> updateGrant(GrantDto grantDto) {
        Grant grant = grantRepository.findById(grantDto.getId()).get();
    
        grant.setStartDate(grantDto.getStartDate());
        grant.setEndDate(grantDto.getEndDate());
        grant.setMonthlyIncome(grantDto.getMonthlyIncome());

        grantRepository.save(grant);

        return grantRepository.findAll().stream().map(GrantDto::new).collect(Collectors.toList());
    }

    public List<GrantDto> deleteGrant(Long id) {
        grantRepository.deleteById(id);
        return grantRepository.findAll().stream().map(GrantDto::new).collect(Collectors.toList());
    }

}
