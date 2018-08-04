package app.task.com.softTsk.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import app.task.com.softTsk.Model.Result;
import app.task.com.softTsk.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Raos
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<Result> productLists;
    private Context context;

    public HomeAdapter(Context context, List<Result> data, OnItemClickListener listener) {
        this.productLists = data;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_adapter, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {

        final Result products = productLists.get(position);
        holder.click(products, listener);
        holder.productName.setText(productLists.get(position).getTitle());
        holder.descrption.setText(productLists.get(position).getByline());
        holder.published_date.setText(productLists.get(position).getPublishedDate());
        String images = productLists.get(position).getMedia().get(0).getMediaMetadata().get(0).getUrl();

        Glide.with(context)
                .load(images)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return productLists.size();
    }

    public interface OnItemClickListener {
        void onClick(Result Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.productName)
        TextView productName;

        @BindView(R.id.buttonDelete)
        TextView buttonDelete;

        @BindView(R.id.descrption)
        TextView  descrption;

        @BindView(R.id.productImage)
        ImageView productImage;

        @BindView(R.id.date)
        TextView published_date;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void click(final Result products, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(products);
                }
            });
        }

    }
}
