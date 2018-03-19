package uk.co.barbuzz.urbancanvas.ui.main.activties;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.barbuzz.urbancanvas.R;
import uk.co.barbuzz.urbancanvas.data.model.Photo;
import uk.co.barbuzz.urbancanvas.ui.base.BaseActivity;
import uk.co.barbuzz.urbancanvas.ui.presenter.MainPresenter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        initViews();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.photo_image_view);
        TextView OwnerTextView = (TextView) findViewById(R.id.owner_text_view);
        TextView DateTakenTextView = (TextView) findViewById(R.id.date_taken_text_view);
        TextView LatLongTextView = (TextView) findViewById(R.id.long_lat_text_view);
        TextView DescTextView = (TextView) findViewById(R.id.desc_text_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        supportPostponeEnterTransition();

        Bundle extras = getIntent().getExtras();
        Photo photo = extras.getParcelable(MainPresenter.EXTRA_PHOTO);

        OwnerTextView.setText(photo.getOwner());
        DateTakenTextView.setText(photo.getDatetaken());
        LatLongTextView.setText(photo.getLongitude() + " / " + photo.getLatitude());
        DescTextView.setText(photo.getDescription().getContent());

        String imageUrl = photo.getLargePhotoUrl();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = extras.getString(MainPresenter.EXTRA_PHOTO_IMAGE_TRANSITION_NAME);
            imageView.setTransitionName(imageTransitionName);
        }

        Picasso.with(this)
                .load(imageUrl)
                .fit()
                .centerCrop()
                .noFade()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        supportStartPostponedEnterTransition();
                    }
                });
    }

}
