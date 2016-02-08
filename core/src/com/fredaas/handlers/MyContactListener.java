package com.fredaas.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {

    private int numContacts;
    private Object fixA;
    private Object fixB;

    private void setFixtures(Contact c) {
        fixA = c.getFixtureA().getUserData();
        fixB = c.getFixtureB().getUserData();
    }

    @Override
    public void beginContact(Contact c) {
        setFixtures(c);

        // Ground
        if (fixA != null && fixA.equals("foot")) {
            numContacts++;
        }
        if (fixB != null && fixB.equals("foot")) {
            numContacts++;
        }
    }

    @Override
    public void endContact(Contact c) {
        setFixtures(c);

        // Ground
        if (fixA != null && fixA.equals("foot")) {
            numContacts--;
        }
        if (fixB != null && fixB.equals("foot")) {
            numContacts--;
        }
    }

    public boolean isPlayerOnGround() {
        return numContacts > 0;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}
