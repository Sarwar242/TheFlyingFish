package com.example.theflyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {

    private Bitmap fish[]= new Bitmap[2];
    private  Bitmap backgroundImage;
    private Paint scorePaint= new Paint();
    private Bitmap life[]=new Bitmap[2];
    private int fishX=10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth, canvasHeight;

    private int yollowX,yollowY, yollowSpeed=16;
    private Paint yollowPaint=new Paint();

    private int greenX,greenY,greenSpeed=20;
    private Paint greenPaint=new Paint();

    private int redX,redY,redSpeed=25;
    private  Paint redPaint= new Paint();

    private int lifeCounterOfFish;

    private boolean touch=false;
    private int score;

    public FlyingFishView(Context context){
        super(context);
        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        backgroundImage=BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yollowPaint.setColor(Color.YELLOW);
        yollowPaint.setAntiAlias(false);


        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY=550;
        score=0;
        lifeCounterOfFish=3;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();


        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishY=fish[0].getHeight();
        int maxFishY=canvasHeight- fish[0].getHeight()*3;

        fishY=fishY+fishSpeed;
        if(fishY<minFishY){
            fishY=minFishY;
        }
        if(fishY>maxFishY){
            fishY=maxFishY;
        }
        fishSpeed=fishSpeed+3;

        if(touch){
            canvas.drawBitmap(fish[1],fishX,fishY,null);

            touch=false;

        }else {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }


        yollowX=yollowX-yollowSpeed;

        if(hitBallChecker(yollowX,yollowY)){
            score=score+10;
            yollowX= -100;
            greenSpeed=greenSpeed+5;
            yollowSpeed=yollowSpeed+5;
            redSpeed=redSpeed+3;
        }


        if (yollowX<0){
            yollowX=canvasWidth+21;
            yollowY=(int)Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawCircle(yollowX,yollowY,25,yollowPaint);


        greenX=greenX-greenSpeed;

        if(hitBallChecker(greenX,greenY)){
            score=score+20;
            greenX= -100;
            greenSpeed=greenSpeed+5;
            yollowSpeed=yollowSpeed+5;
            redSpeed=redSpeed+3;

        }


        if (greenX<0){
            greenX=canvasWidth+21;
            greenY=(int)Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawCircle(greenX,greenY,28,greenPaint);


        redX=redX-redSpeed;

        if(hitBallChecker(redX,redY)){
            redX= -100;
            lifeCounterOfFish-=1;
            greenSpeed=16;
            yollowSpeed=20;
            redSpeed=25;

            if (lifeCounterOfFish==0){
                Toast.makeText(getContext(),"Game Over!",Toast.LENGTH_SHORT).show();

                Intent gameOverIntent= new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.putExtra("Score",score);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


                getContext().startActivity(gameOverIntent);
            }
        }


        if (redX<0){
            redX=canvasWidth+21;
            redY=(int)Math.floor(Math.random()*(maxFishY-minFishY))+minFishY;
        }
        canvas.drawCircle(redX,redY,32,redPaint);
        canvas.drawText("Score : "+score,20,60,scorePaint);

        for (int i=0;i<3;i++){
            int x=(int) (580+life[0].getWidth()*1.5*i);
            int y=30;

            if(i<lifeCounterOfFish){
                canvas.drawBitmap(life[0],x,y,null);
            }
            else
            {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }



    }

    public boolean hitBallChecker(int x, int y){
        if(fishX<x && x<(fishX+fish[0].getWidth())&&fishY<y && y<(fishY+fish[0].getWidth())){
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            touch=true;
            fishSpeed=-30;


        }
        return true;
    }
}
