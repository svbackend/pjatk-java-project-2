package puzzle.Interfaces;

import puzzle.ScreenChangerService;

public interface IController {
    void setScreenChanger(ScreenChangerService screenPage);
    void setParameterBag(IParameterBag parameterBag);
}
