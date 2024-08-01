package pt.ulisboa.tecnico.rnl.dei.dms.grants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.CMSException;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.domain.Grant;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.dto.GrantDto;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.repository.GrantRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.utils.DateHandler;

@Service
public class GrantService {

    @Autowired
    private GrantRepository grantRepository;

    public GrantDto createGrant(GrantDto grantDto) {

        if( grantDto.getStartDate() == null) {
            throw new CMSException(ErrorMessage.GRANT_START_DATE_CANNOT_BE_EMPTY);
        }

        if( grantDto.getEndDate() == null) {
            throw new CMSException(ErrorMessage.GRANT_END_DATE_CANNOT_BE_EMPTY);
        }

        Grant grant = new Grant(grantDto);
        grantRepository.save(grant);
        return new GrantDto(grant);
    }

    public List<GrantDto> getGrants() {
        return grantRepository.findAll().stream().map(GrantDto::new).collect(Collectors.toList());
    }

    public List<GrantDto> updateGrant(GrantDto grantDto) {
        Grant grant = grantRepository.findById(grantDto.getId()).get();

        grant.update(grantDto);
        grantRepository.save(grant);

        return grantRepository.findAll().stream().map(GrantDto::new).collect(Collectors.toList());
    }

    public List<GrantDto> deleteGrant(Long id) {
        grantRepository.deleteById(id);
        return grantRepository.findAll().stream().map(GrantDto::new).collect(Collectors.toList());
    }

    public GrantDto getGrantById(Long id) {

        try {
            Grant grant = grantRepository.findById(id).get();
            return new GrantDto(grant);
        } catch (Exception e) {
            throw new CMSException(ErrorMessage.GRANT_NOT_FOUND);
        }
    }

}
