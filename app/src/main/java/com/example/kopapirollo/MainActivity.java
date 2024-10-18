package com.example.kopapirollo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random random;
    int cchoice;
    int pchoice;
    int cpuHp;
    int playerHp;
    int tie;
    ImageView cpu_heart1;
    ImageView cpu_heart2;
    ImageView cpu_heart3;
    ImageView player_heart1;
    ImageView player_heart2;
    ImageView player_heart3;
    ImageView cpuChoice;
    ImageView playerChoice;
    ImageButton rockButton;
    ImageButton paperButton;
    ImageButton scissorsButton;
    TextView tieText;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Init();

        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pchoice = 0;
                StartGame();
            }
        });
        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pchoice = 1;
                StartGame();
            }
        });
        scissorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pchoice = 2;
                StartGame();
            }
        });
    }

    void StartGame(){
        cchoice = random.nextInt(3);
        int roundWonBy = -1;
        if(pchoice == cchoice){
            tie++;
            UpdateUI(roundWonBy);
            return;
        }

        switch (cchoice) {
            case 0:
                if(pchoice == 1){
                   cpuHp--;
                   roundWonBy = 0;
                }else{
                    playerHp--;
                    roundWonBy = 1;
                }
                break;
            case 1:
                if(pchoice == 2){
                    cpuHp--;
                    roundWonBy = 0;
                }else{
                    playerHp--;
                    roundWonBy = 1;
                }
                break;
            case 2:
                if(pchoice == 0){
                    cpuHp--;
                    roundWonBy = 0;
                }else{
                    playerHp--;
                    roundWonBy = 1;
                }
                break;
        }
        UpdateUI(roundWonBy);
        CheckWin();
    }

    void CheckWin(){
        if(cpuHp == 0){
            alertDialog.setTitle("Győzelem!");
            alertDialog.show();
        }else if(playerHp == 0){
            alertDialog.setTitle("Vereség!");
            alertDialog.show();
        }
    }

    void RoundWonBy(int who){
        if(who == 0)
            Toast.makeText(this, "A játékos nyert!", Toast.LENGTH_SHORT).show();
        else if(who == 1)
            Toast.makeText(this, "A gép nyert!", Toast.LENGTH_SHORT).show();
        else if(who == -1)
            Toast.makeText(this, "Döntetlen!", Toast.LENGTH_SHORT).show();
    }

    void UpdateUI(int whoWon){
        cpuChoice.setImageResource(ChoiceToImage(cchoice));
        playerChoice.setImageResource(ChoiceToImage(pchoice));
        tieText.setText("Döntetlenek száma: " + tie);
        RoundWonBy(whoWon);
        UpdateHearts();
    }

    void UpdateHearts(){
        switch (cpuHp) {
            case 3:
                cpu_heart1.setImageResource(R.drawable.heart2);
                cpu_heart2.setImageResource(R.drawable.heart2);
                cpu_heart3.setImageResource(R.drawable.heart2);
                break;
            case 2:
                cpu_heart3.setImageResource(R.drawable.heart1);
                break;
            case 1:
                cpu_heart2.setImageResource(R.drawable.heart1);
                break;
            case 0:
                cpu_heart1.setImageResource(R.drawable.heart1);
                break;
        }

        switch (playerHp) {
            case 3:
                player_heart1.setImageResource(R.drawable.heart2);
                player_heart2.setImageResource(R.drawable.heart2);
                player_heart3.setImageResource(R.drawable.heart2);
                break;
            case 2:
                player_heart3.setImageResource(R.drawable.heart1);
                break;
            case 1:
                player_heart2.setImageResource(R.drawable.heart1);
                break;
            case 0:
                player_heart1.setImageResource(R.drawable.heart1);
                break;
        }
    }

    int ChoiceToImage(int id){
        switch (id){
            case 0:
                return R.drawable.rock;
            case 1:
                return R.drawable.paper;
            case 2:
                return R.drawable.scissors;
            default:
                return 0;
        }
    }
    void ResetGame(){
        cpuHp = 3;
        playerHp = 3;
        tie = 0;
        UpdateUI(-2);
    }

    void Init(){
        random = new Random();
        cchoice = 0;
        pchoice = 0;
        cpuHp = 3;
        playerHp = 3;
        tie = 0;
        cpu_heart1 = findViewById(R.id.cpu_heart1);
        cpu_heart2 = findViewById(R.id.cpu_heart2);
        cpu_heart3 = findViewById(R.id.cpu_heart3);
        player_heart1 = findViewById(R.id.player_heart1);
        player_heart2 = findViewById(R.id.player_heart2);
        player_heart3 = findViewById(R.id.player_heart3);
        cpuChoice = findViewById(R.id.cpuImage);
        playerChoice = findViewById(R.id.playerImage);
        rockButton = findViewById(R.id.rockButton);
        paperButton = findViewById(R.id.paperButton);
        scissorsButton = findViewById(R.id.scissorsButton);
        tieText = findViewById(R.id.tieText);
        alertDialog = new AlertDialog.Builder(this)
                .setMessage("Szeretne uj jatekot jatszani?")
                .setPositiveButton("IGEN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ResetGame();
                    }
                })
                .setNegativeButton("NEM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).create();
    }
}