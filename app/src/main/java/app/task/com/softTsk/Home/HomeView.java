package app.task.com.softTsk.Home;


import java.util.List;

import app.task.com.softTsk.Model.Result;

/**
 * Created by ennur on 6/25/16.
 */
public interface HomeView {

    void showProgress();

    void hideProgress();

    void onResponse(List<Result> articleLst);

    void onFailure(Throwable t);


}
