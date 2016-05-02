package com.snapttechtechnologies.stevekamau.wehappening.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.flyco.animation.BaseAnimatorSet;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.app.AppController;
import com.snapttechtechnologies.stevekamau.wehappening.helper.DataBaseHandler;
import com.snapttechtechnologies.stevekamau.wehappening.model.AttendingListItems;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Detail extends BaseActivity implements ObservableScrollViewCallbacks {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static String Title = "title";
    private static String Time = "time";
    private static String Details = "details";
    private static String Where = "where";
    private static String Date = "date";
    private static String bitmap = "thumbnailUrl";
    Animation myAnimation;
    String mPath;
    TextView time_left;
    String time, where;
    String newDateStr;
    CheckBox checkBox;
    ImageView img;
    TextView myText;
    TextView title_txt, detail_txt, where_txt, time_txt, event_txt, attend_txt, a1;
    EditText ed1, ed2;
    RelativeLayout time_layout, attend_layout;
    FloatingActionButton floatingActionButton;
    AlertDialog dialog;
    NetworkImageView thumbNail;
    File imageFile;
    int currentIndex = -1; // to keep current Index
    // Array of String to Show In TextSwitcher
    // Declare in and out Animations
    Animation in, out;
    java.util.Date date1;
    java.util.Date date2;
    Context mContext;
    private BaseAnimatorSet bas_in;
    private BaseAnimatorSet bas_out;
    private int mFabMargin;
    private View mFab;
    private ImageView img2;
    private View mImageView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    private TextSwitcher mSwitcher;
    private Handler mHandler = new Handler();

    // Create a Runnable Instance
    Runnable r = new Runnable() {
        // Override the run Method
        public void run() {
            // TODO Auto-generated method stub
            try {
                // Update the TextSwitcher text
                updateTextSwitcherText();

            } finally {
                mHandler.postDelayed(this, 3000);
            }
        }
    };

    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mImageView = findViewById(R.id.image);
        mToolbarView = findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.colorPrimary)));
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);


        Intent i = getIntent();
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        final String bitmap = i.getStringExtra("images");
        thumbNail = (NetworkImageView) findViewById(R.id.img2);
        thumbNail.setImageUrl(bitmap, imageLoader);
        final NetworkImageView layout = (NetworkImageView) findViewById(R.id.image);
        layout.setImageUrl(bitmap, imageLoader);

        final String name = i.getStringExtra(Title);
        String date = i.getStringExtra(Date);
        String details = i.getStringExtra(Details);
        time = i.getStringExtra(Time);
        where = i.getStringExtra(Where);


        title_txt = (TextView) findViewById(R.id.title_label);
        title_txt.setText(name);
        detail_txt = (TextView) findViewById(R.id.body);
        detail_txt.setText(details);
        time_txt = (TextView) findViewById(R.id.time_label);
        time_txt.setText(time);
        where_txt = (TextView) findViewById(R.id.where_label);
        where_txt.setText(where);
        event_txt = (TextView) findViewById(R.id.event_txt);
        attend_txt = (TextView) findViewById(R.id.attend);

        String fontPath = "fonts/Centurygothic.ttf";
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), fontPath);
        title_txt.setTypeface(tf);
        event_txt.setTypeface(tf);
        attend_txt.setTypeface(tf);
        detail_txt.setTypeface(tf);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
                shareDialog();
            }
        });

        mFabMargin = 15;
        showFab();

        myAnimation = AnimationUtils.loadAnimation(this, R.anim.text_animation);
        //time_left.startAnimation(myAnimation);
        attend_txt.startAnimation(myAnimation);

        attend_layout = (RelativeLayout) findViewById(R.id.relative_attend);
        attend_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();

                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Add event to your attending list")
                        .setCancelText("No, cancel")
                        .setConfirmText("Yes, sure!")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance, keep widget user state, reset them if you need
                                sDialog.setTitleText("Cancelled!")
                                        .setContentText("Event not added")
                                        .setConfirmText("Okay")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("Event Added")
                                        .setContentText("We'll See you there :)")
                                        .setConfirmText("Awesome")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                insert();
                            }
                        })
                        .show();
            }
        });


        mContext = this;
        mHandler.postDelayed(r, 3000);
        mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);

        // Set the ViewFactory of the TextSwitcher that will create TextView object when asked
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                myText = new TextView(getApplicationContext());
                myText.setGravity(Gravity.CENTER);
                myText.setTextSize(15);
                myText.setPadding(2, 5, 2, 5);
                myText.setBackgroundResource(R.drawable.rounded_blue_background);
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });

        // initialize the in and out  animations
        in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        mSwitcher.setInAnimation(in);
        mSwitcher.setOutAnimation(out);
        DateDifference();
        convertDateAndTime();
    }

    private void showFab() {
        ViewPropertyAnimator.animate(mFab).cancel();
        ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
    }

    public void DateDifference() {
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("E d MMMM yyyy");
        String CurrentDate = sdf.format(date);
        String FinalDate = time_txt.getText().toString();

        SimpleDateFormat dates = new SimpleDateFormat("E d MMMM yyyy");

        //Setting dates
        try {
            date1 = dates.parse(CurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = dates.parse(FinalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Comparing dates
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);

        //Convert long to String
        String dayDifference = Long.toString(differenceDates);
        time_left = (TextView) findViewById(R.id.time_left);
        if (dayDifference.contains("1")) {
            time_left.setText(dayDifference + " day left");
        } else {

            time_left.setText(dayDifference + " days left");
        }

    }

    private void convertDateAndTime() {
        SimpleDateFormat format = new SimpleDateFormat("E d MMMM yyyy kk:mm", Locale.ENGLISH);
        java.util.Date date = null;
        try {
            date = format.parse(time_txt.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        newDateStr = postFormater.format(date);
    }

    private void updateTextSwitcherText() {
        String textToShow[] = {where_txt.getText().toString(), time_txt.getText().toString()};
        int messageCount = textToShow.length;
        currentIndex++;
        if (currentIndex == messageCount)
            currentIndex = 0;
        mSwitcher.setText(textToShow[currentIndex]);
    }

    private void saveImage() {
        thumbNail.buildDrawingCache();
        Bitmap bm = thumbNail.getDrawingCache();

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/weHappening/saved_images");
        myDir.mkdirs();
        String fname = "Image-" + title_txt.getText().toString() + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getApplicationContext(), root + "/weHappening/saved_images", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Didnt Work", Toast.LENGTH_SHORT).show();
        }
    }

    private void insert() {

        DataBaseHandler db = new DataBaseHandler(this);
// get image from directory
        String root = Environment.getExternalStorageDirectory().toString();
        File imgFile = new File(root + "/weHappening/saved_images/" + "Image-" + title_txt.getText().toString() + ".jpg");

        if (imgFile.exists()) {

            Bitmap image = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

// convert bitmap to byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte imageInByte[] = stream.toByteArray();
/**
 * CRUD Operations
 * */
// Inserting Contacts
            Log.d("Insert: ", "Inserting ..");
            db.addContact(new AttendingListItems(title_txt.getText().toString(), imageInByte, detail_txt.getText().toString(),
                    time_txt.getText().toString(), where_txt.getText().toString(), newDateStr));
        }


    }

    private void success() {

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.colorPrimary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(mImageView, scrollY / 2);
        //time_left.startAnimation(myAnimation);
        attend_txt.startAnimation(myAnimation);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareDialog() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.share, null);
        android.support.v7.app.AlertDialog.Builder adb = new android.support.v7.app.AlertDialog.Builder(this);
        a1 = (TextView) layout.findViewById(R.id.id1);
        String fontPath = "fonts/Centurygothic.ttf";
        Typeface tf = Typeface.createFromAsset(this.getAssets(), fontPath);
        a1.setTypeface(tf);
        ed1 = (EditText) layout.findViewById(R.id.e1);
        ed2 = (EditText) layout.findViewById(R.id.e2);
        ed1.setText("weHappening App for Android");
        ed2.setText(detail_txt.getText().toString());
        img = (ImageView) layout.findViewById(R.id.screenshot);
        img.setVisibility(View.GONE);
        checkBox = (CheckBox) layout.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    File path = new File(Environment.getExternalStorageDirectory(), "/weHappening/Screenshot.jpg");
                    Bitmap b = null;
                    if (path.exists()) {
                        try {
                            b = BitmapFactory.decodeStream(new FileInputStream(path));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        img.setVisibility(View.VISIBLE);
                        img.setImageBitmap(b);
                    }
                } else {
                    img.setVisibility(View.GONE);
                }

            }
        });

        adb.setView(layout);
        adb.setCancelable(false);

        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (checkBox.isChecked()) {
                    sendMailWithScreenshot(mPath);
                } else {
                    Toast.makeText(getApplicationContext(), "Didnt Work", Toast.LENGTH_SHORT).show();
                }

            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.show();
    }

    public void sendMailWithScreenshot(String path) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{"youremail@website.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                ed1.getText().toString());
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                ed2.getText().toString());
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share with friends..."));
    }

    private void takeScreenshot() {
        try {
            // image naming and path  to include sd card  appending name you choose for file
            mPath = Environment.getExternalStorageDirectory().toString() + "/weHappening/" + "Screenshot" + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

}
