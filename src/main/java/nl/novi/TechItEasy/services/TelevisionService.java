package nl.novi.TechItEasy.services;

import nl.novi.TechItEasy.Repositories.TelevisionRepository;
import nl.novi.TechItEasy.dtos.TelevisionOutputDto;
import nl.novi.TechItEasy.dtos.TelevisionInputDto;
import nl.novi.TechItEasy.exceptions.RecordNotFoundException;
import nl.novi.TechItEasy.models.Television;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelevisionService {
    private final TelevisionRepository televisionRepository;

    public TelevisionService(TelevisionRepository televisionRepository) {
        this.televisionRepository = televisionRepository;
    }

    public TelevisionOutputDto getTelevision(int id) {
        Optional<Television> optionalTelevision = televisionRepository.findById(id);
        if (televisionRepository.existsById(id)) {
            Television television1 = optionalTelevision.get();
            return transferToOutput(television1);
        } else {
            throw new RecordNotFoundException(id + " not found");
        }
    }

    public List<TelevisionOutputDto> getTelevisions() {
        if (televisionRepository.count() > 0) {
            List<Television> allTelevisions = televisionRepository.findAll();
            ArrayList<TelevisionOutputDto> resultList = new ArrayList<>();
            for (Television television: allTelevisions) {
                TelevisionOutputDto newTelevisionOutputDto = new TelevisionOutputDto();
                newTelevisionOutputDto.setType(television.getType());
                newTelevisionOutputDto.setBrand(television.getBrand());
                newTelevisionOutputDto.setName(television.getName());
                newTelevisionOutputDto.setPrice(television.getPrice());
                newTelevisionOutputDto.setAvailableSize(television.getAvailableSize());
                newTelevisionOutputDto.setRefreshRate(television.getRefreshRate());
                newTelevisionOutputDto.setScreenType(television.getScreenType());
                newTelevisionOutputDto.setScreenQuality(television.getScreenQuality());
                newTelevisionOutputDto.setSmartTv(television.isSmartTv());
                newTelevisionOutputDto.setWifi(television.isWifi());
                newTelevisionOutputDto.setVoiceControl(television.isVoiceControl());
                newTelevisionOutputDto.setHdr(television.isHdr());
                newTelevisionOutputDto.setBluetooth(television.isBluetooth());
                newTelevisionOutputDto.setAmbiLight(television.isAmbiLight());
                newTelevisionOutputDto.setOriginalStock(television.getOriginalStock());
                newTelevisionOutputDto.setSold(television.getSold());
                resultList.add(newTelevisionOutputDto);
            }
            return resultList;
        } else {
            throw new RecordNotFoundException("No televisions found");
        }
    }

    public int saveTelevision(TelevisionInputDto dto) {
        if (televisionRepository.existsByName(dto.getName())) {
            throw new RecordNotFoundException("Television is allready in our list!");
        }
        Television newTelevision = new Television();

        newTelevision.setType(dto.getType());
        newTelevision.setBrand(dto.getBrand());
        newTelevision.setName(dto.getName());
        newTelevision.setPrice(dto.getPrice());
        newTelevision.setAvailableSize(dto.getAvailableSize());
        newTelevision.setRefreshRate(dto.getRefreshRate());
        newTelevision.setScreenType(dto.getScreenType());
        newTelevision.setScreenQuality(dto.getScreenQuality());
        newTelevision.setSmartTv(dto.isSmartTv());
        newTelevision.setWifi(dto.isWifi());
        newTelevision.setVoiceControl(dto.isVoiceControl());
        newTelevision.setHdr(dto.isHdr());
        newTelevision.setBluetooth(dto.isBluetooth());
        newTelevision.setAmbiLight(dto.isAmbiLight());
        newTelevision.setOriginalStock(dto.getOriginalStock());
        newTelevision.setSold(dto.getSold());

        Television savedTelevision = televisionRepository.save(newTelevision);
        return savedTelevision.getId();
    }

    public TelevisionOutputDto updateTelevision(int id, TelevisionInputDto televisionInputDto) {
        Optional<Television> updatedTelevision = televisionRepository.findById(id);
        if (updatedTelevision.isPresent()) {

            Television newTelevision = updatedTelevision.get();
            if (televisionInputDto.getType() != null) {
                newTelevision.setType(televisionInputDto.getType());
            }
            if (televisionInputDto.getBrand() != null) {
                newTelevision.setBrand(televisionInputDto.getBrand());
            }
            if (televisionInputDto.getName() != null) {
                newTelevision.setName(televisionInputDto.getName());
            }
            if(televisionInputDto.getPrice() > 0) {
                newTelevision.setPrice(televisionInputDto.getPrice());
            }
            if(televisionInputDto.getAvailableSize() > 0) {
                newTelevision.setAvailableSize(televisionInputDto.getAvailableSize());
            }
            if(televisionInputDto.getRefreshRate() > 0) {
                newTelevision.setRefreshRate(televisionInputDto.getRefreshRate());
            }
            if (televisionInputDto.getScreenType() != null) {
                newTelevision.setScreenType(televisionInputDto.getScreenType());
            }
            if (televisionInputDto.getScreenQuality() != null) {
                newTelevision.setScreenQuality(televisionInputDto.getScreenQuality());
            }
            if(televisionInputDto.isSmartTv() != newTelevision.isSmartTv()) {
                newTelevision.setSmartTv(televisionInputDto.isSmartTv());
            }
            if(televisionInputDto.isWifi() != newTelevision.isWifi()) {
                newTelevision.setWifi(televisionInputDto.isWifi());
            }
            if(televisionInputDto.isVoiceControl() != newTelevision.isVoiceControl()) {
                newTelevision.setVoiceControl(televisionInputDto.isVoiceControl());
            }
            if(televisionInputDto.isHdr() != newTelevision.isHdr()) {
                newTelevision.setHdr(televisionInputDto.isHdr());
            }
            if(televisionInputDto.isBluetooth() != newTelevision.isBluetooth()) {
                newTelevision.setBluetooth(televisionInputDto.isBluetooth());
            }
            if(televisionInputDto.isAmbiLight() != newTelevision.isAmbiLight()) {
                newTelevision.setAmbiLight(televisionInputDto.isAmbiLight());
            }
            if(televisionInputDto.getOriginalStock() > 0) {
                newTelevision.setOriginalStock(televisionInputDto.getOriginalStock());
            }
            if(televisionInputDto.getSold() > 0) {
                newTelevision.setSold(televisionInputDto.getSold());
            }

            televisionRepository.save(newTelevision);
            return transferToOutput(newTelevision);
        } else {
            throw  new RecordNotFoundException("The id/body input are invalid");
        }
    }

    public void deleteTelevision(String name) {
        Optional <Television> deleteTelevision = televisionRepository.findByName(name);
        if (televisionRepository.existsByName(name)) {
            televisionRepository.delete(deleteTelevision.get());
        } else {
            throw new RecordNotFoundException("No television found!");
        }
    }

    public Television transferToInput(TelevisionInputDto televisionInputDto) {
        Television television = new Television();

        television.setType(televisionInputDto.getType());
        television.setBrand(televisionInputDto.getBrand());
        television.setName(televisionInputDto.getName());
        television.setPrice(televisionInputDto.getPrice());
        television.setAvailableSize(televisionInputDto.getAvailableSize());
        television.setRefreshRate(televisionInputDto.getRefreshRate());
        television.setScreenType(televisionInputDto.getScreenType());
        television.setScreenQuality(televisionInputDto.getScreenQuality());
        television.setSmartTv(televisionInputDto.isSmartTv());
        television.setWifi(televisionInputDto.isWifi());
        television.setVoiceControl(televisionInputDto.isVoiceControl());
        television.setHdr(televisionInputDto.isHdr());
        television.setBluetooth(televisionInputDto.isBluetooth());
        television.setAmbiLight(televisionInputDto.isAmbiLight());
        television.setOriginalStock(televisionInputDto.getOriginalStock());
        television.setSold(televisionInputDto.getSold());
        return television;
    }

    public TelevisionOutputDto transferToOutput(Television television) {
        TelevisionOutputDto tvOutputDto = new TelevisionOutputDto();

        tvOutputDto.setType(television.getType());
        tvOutputDto.setBrand(television.getBrand());
        tvOutputDto.setName(television.getName());
        tvOutputDto.setPrice(television.getPrice());
        tvOutputDto.setAvailableSize(television.getAvailableSize());
        tvOutputDto.setRefreshRate(television.getRefreshRate());
        tvOutputDto.setScreenType(television.getScreenType());
        tvOutputDto.setScreenQuality(television.getScreenQuality());
        tvOutputDto.setSmartTv(television.isSmartTv());
        tvOutputDto.setWifi(television.isWifi());
        tvOutputDto.setVoiceControl(television.isVoiceControl());
        tvOutputDto.setHdr(television.isHdr());
        tvOutputDto.setBluetooth(television.isBluetooth());
        tvOutputDto.setAmbiLight(television.isAmbiLight());
        tvOutputDto.setOriginalStock(television.getOriginalStock());
        tvOutputDto.setSold(television.getSold());

        return tvOutputDto;
    }
}
