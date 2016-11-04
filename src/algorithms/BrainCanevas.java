/* ******************************************************
 * Simovies - Eurobot 2015 Robomovies Simulator.
 * Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/BrainCanevas.java 2014-10-19 buixuan.
 * ******************************************************/
package algorithms;

import robotsimulator.Brain;

import java.util.ArrayList;
import java.util.Random;

import characteristics.IFrontSensorResult;
import characteristics.IRadarResult;
import characteristics.Parameters;

public class BrainCanevas extends Brain {
  public BrainCanevas() { super(); }

  private Random gen;

  private boolean turnTask,turnRight,moveTask,berzerk,back,highway;
  private double endTaskDirection,lastSeenDirection,lastShot;
  private int endTaskCounter,berzerkInerty;
  private boolean firstMove,berzerkTurning;
  private boolean isEst,isNorth;
  private static final double PRECISION = 0.001;
  

  public void activate() {
    //---PARTIE A MODIFIER/ECRIRE---//
    move();
  }
  public void step() {
    //---PARTIE A MODIFIER/ECRIRE---//

	  ArrayList<IRadarResult> radarResults = detectRadar();
	 
	  
///// CAMPBOT
      for (IRadarResult r : radarResults) {
    	  
        if (r.getObjectType()==IRadarResult.Types.OpponentMainBot) {
          fire(r.getObjectDirection());
//          lastShot=r.getObjectDirection();
          return;
        }
      }
//      fire(lastShot);
	  
	 if (jeVoisAmis(radarResults)) {
		 stepTurn(Parameters.Direction.LEFT);

		 if (directionNord()) {
			 move();
		 }
	 }
	 
	 if (jeVoisUnMur()) {
		 stepTurn(Parameters.Direction.RIGHT);

		 if (directionSud()) {
			 move();
		 }
	 } else {
		 
	 }
	  
	  
//	  if (radarResults.size()!=0){
	      for (IRadarResult r : radarResults) {
	        if (r.getObjectType()==IRadarResult.Types.OpponentMainBot) {
	          berzerk=true;
	          back=(Math.cos(getHeading()-r.getObjectDirection())>0);
	          endTaskCounter=21;
	          fire(r.getObjectDirection());
	          lastSeenDirection=r.getObjectDirection();
	          berzerkTurning=true;
	          endTaskDirection=lastSeenDirection;
	          double ref=endTaskDirection-getHeading();
	          if (ref<0) ref+=Math.PI*2;
	          turnRight=(ref>0 && ref<Math.PI);
	          return;
	        }
	      }
	      for (IRadarResult r : radarResults) {
	        if (r.getObjectType()==IRadarResult.Types.OpponentSecondaryBot) {
	          fire(r.getObjectDirection());
	          return;
	        }
	      }
//	    }
	  
    if (detectFront().getObjectType()==IFrontSensorResult.Types.NOTHING) {
      move();
    }
  }
  
  private boolean jeVoisUnMur(){
	    return (detectFront().getObjectType()==IFrontSensorResult.Types.WALL);
	  }
  private boolean jeVoisAmis(ArrayList<IRadarResult> objetsDetectes){
	    for (IRadarResult res: objetsDetectes){
	      if ( res.getObjectType()==IRadarResult.Types.TeamMainBot ||
	            res.getObjectType()==IRadarResult.Types.TeamSecondaryBot
	          ){
	        return true;
	      }
	    }
	    return false;
	  }
	  private boolean directionSud(){
	    return (Math.abs(getHeading()-Parameters.SOUTH) < PRECISION);
	  }
	  private boolean directionEst(){
	    return (Math.abs(getHeading()-Parameters.EAST) < PRECISION);
	  }
	  private boolean directionNord(){
	    return (Math.abs(getHeading()-Parameters.NORTH) < PRECISION);
	  }
}


