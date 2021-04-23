package com.geraldo.beercontrol.service;

import com.geraldo.beercontrol.DTO.BeerDTO;
import com.geraldo.beercontrol.entity.Beer;
import com.geraldo.beercontrol.exception.BeerAlreadyRegisteredException;
import com.geraldo.beercontrol.exception.BeerNotFoundException;
import com.geraldo.beercontrol.exception.BeerStockExceededException;
import com.geraldo.beercontrol.mapper.BeerMapper;
import com.geraldo.beercontrol.repository.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerService {


    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper = BeerMapper.INSTANCE;


    public BeerDTO createBeer(BeerDTO beerDTO){
        Beer beer = beerMapper.toModel(beerDTO);
        Beer salvarBeer = beerRepository.save(beer);
        return beerMapper.toDTO(salvarBeer);

    }

    public List<BeerDTO> listAll(){
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BeerDTO findByName(String nome) throws BeerNotFoundException {
        Beer foundBeer = beerRepository.findByName(nome)
                .orElseThrow(()-> new BeerNotFoundException(nome));
      return beerMapper.toDTO(foundBeer);
    }
    public void deleteById(Long id) throws BeerNotFoundException {
        verifyIfExists(id);
        beerRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws BeerAlreadyRegisteredException {
        Optional<Beer> optSavedBeer = beerRepository.findByName(name);
        if (optSavedBeer.isPresent()) {
            throw new BeerAlreadyRegisteredException(name);
        }
    }

    private Beer verifyIfExists(Long id) throws BeerNotFoundException {
        return beerRepository.findById(id)
                .orElseThrow(() -> new BeerNotFoundException(id));
    }

    public BeerDTO increment(Long id, int quantityToIncrement) throws BeerNotFoundException, BeerStockExceededException {
        Beer beerToIncrementStock = verifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + beerToIncrementStock.getQuantity();
        if (quantityAfterIncrement <= beerToIncrementStock.getMax()) {
            beerToIncrementStock.setQuantity(beerToIncrementStock.getQuantity() + quantityToIncrement);
            Beer incrementedBeerStock = beerRepository.save(beerToIncrementStock);
            return beerMapper.toDTO(incrementedBeerStock);
        }
        throw new BeerStockExceededException(id, quantityToIncrement);
    }

}
