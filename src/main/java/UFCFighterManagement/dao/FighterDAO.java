package UFCFighterManagement.dao;

import UFCFighterManagement.dto.FighterDTO;
import UFCFighterManagement.filter.FighterFilter;

import java.util.List;

public interface FighterDAO {
    List<FighterDTO> getAllFighters();
    FighterDTO getFighterById(int id);
    boolean deleteFighterById(int id);
    FighterDTO insertFighter(FighterDTO fighter);
    boolean updateFighter(int id, FighterDTO fighter);
    List<FighterDTO> findFightersApplyFilter(FighterFilter filter);
}

