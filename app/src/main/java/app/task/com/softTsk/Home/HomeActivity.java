package app.task.com.softTsk.Home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import app.task.com.softTsk.Model.Result;
import app.task.com.softTsk.Network.ErrorUtils;
import app.task.com.softTsk.R;
import app.task.com.softTsk.Utils.ConnectionDetector;
import app.task.com.softTsk.Utils.Util;
import app.task.com.softTsk.ViewManager.ViewManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerViewList;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ProgressDialog pd;
    private HomePresenter homePresenter;
    private LinearLayoutManager linearLayoutManager;
    private HomeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        pd = new Util().waitingMessage(HomeActivity.this);

        recyclerViewList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewList.setLayoutManager(linearLayoutManager);


        homePresenter = new HomePresenter(this);
        loadHomePage();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadHomePage();
            }
        });
    }

    private void loadHomePage() {

        if (new ConnectionDetector(this).isConnectingToInternet()) {
            homePresenter.onLoadData();
        } else {
            Toast.makeText(HomeActivity.this, getResources().getString(R.string.noInternet), Toast.LENGTH_LONG).show();
        }
    }

    @Override protected void onDestroy() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
        homePresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        pd.show();
    }

    @Override
    public void hideProgress() {
        if (pd.isShowing()) {
            pd.hide();
        }
    }

    @Override
    public void onResponse(List<Result> productLst) {
        try {
            if (productLst != null) {
               // ProductList productList = response.body();
                onListLoadData(productLst);
            } else {
                Toast.makeText(this, "Please try Later", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        String ErrMSg = ErrorUtils.responseError(this, t);
        Toast.makeText(this, ErrMSg, Toast.LENGTH_SHORT).show();

        Log.e("onFailure", "" + t);
    }

    private void onListLoadData(List<Result> productList) {
        adapter = new HomeAdapter(getApplicationContext(), productList, new HomeAdapter.OnItemClickListener() {
            @Override
            public void onClick(Result products) {
                String url = products.getUrl();
                new ViewManager().gotoProductDetailsView(HomeActivity.this, url);
            }
        });

        recyclerViewList.setAdapter(adapter);
    }


}
