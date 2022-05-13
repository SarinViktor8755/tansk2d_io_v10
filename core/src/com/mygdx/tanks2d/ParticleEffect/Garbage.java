package com.mygdx.tanks2d.ParticleEffect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Garbage {
    private Vector2 pos;
    private Vector2 velocity;
    private float timeLife;
    private float rot;


    public Garbage() {
        this.pos = new Vector2();
        this.velocity = new Vector2();
        this.timeLife = 0;
        this.rot = 0;
    }

    public void upDate(){
        if(!isLife()) return;
        if (timeLife > 0) timeLife -= Gdx.graphics.getDeltaTime();
        pos.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
    }

    private boolean isLife() {
        if (timeLife > 0) return true;
        return false;
    }

    public void  setParametors(float x ,float y){
        this.pos.set(x,y);
        this.velocity.set(MathUtils.random(-1f,1f),MathUtils.random(-1f,1f)).scl(500);
        this.timeLife = .07f;
        this.rot = MathUtils.random(0,360);
    }

    public float getRot() {
        return rot;
    }

    public Vector2 getPos() {
        return pos;
    }
}
