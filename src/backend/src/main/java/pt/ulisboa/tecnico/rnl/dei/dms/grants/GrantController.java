package pt.ulisboa.tecnico.rnl.dei.dms.grants;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.grants.dto.GrantDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/grants")
public class GrantController {
    
    @Autowired
    private GrantService grantService;

    @GetMapping(value = "/all",
                produces = "application/json; charset=utf-8")
    public List<GrantDto>
    getGrants() {
      return grantService.getGrants();
    }

    @PostMapping("/create")
    public GrantDto createGrant(@RequestBody GrantDto grantDto) {
        return grantService.createGrant(grantDto);
    }

    @PutMapping("/update")
    public List<GrantDto> updateGrant(@RequestBody GrantDto grantDto) {
        return grantService.updateGrant(grantDto);
    }

    @DeleteMapping("/delete/{id}")
    public List<GrantDto> deleteGrant(@PathVariable Long id) {
        return grantService.deleteGrant(id);
    }

    @GetMapping("/{id}")
    public GrantDto getGrantById(@PathVariable Long id) {
        return grantService.getGrantById(id);
    }
    
}
