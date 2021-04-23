package com.geraldo.beercontrol.controler;

import com.geraldo.beercontrol.DTO.BeerDTO;
import com.geraldo.beercontrol.DTO.QuantityDTO;
import com.geraldo.beercontrol.exception.BeerNotFoundException;
import com.geraldo.beercontrol.exception.BeerStockExceededException;
import com.geraldo.beercontrol.service.BeerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/beer")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerController {

    private final BeerService beerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO createBeer(@RequestBody @Valid BeerDTO beerDTO){
        return beerService.createBeer(beerDTO);
    }

    @GetMapping
    public List<BeerDTO> listBeers(){
        return beerService.listAll();
    }

    @GetMapping("/{nome}")
    public BeerDTO findByName(@PathVariable String nome) throws BeerNotFoundException {
        return beerService.findByName(nome);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws BeerNotFoundException {
        beerService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public BeerDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerNotFoundException, BeerStockExceededException {
        return beerService.increment(id, quantityDTO.getQuantity());
    }

}
