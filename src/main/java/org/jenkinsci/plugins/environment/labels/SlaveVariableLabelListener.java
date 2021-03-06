package org.jenkinsci.plugins.environment.labels;

import hudson.Extension;
import hudson.model.Computer;
import hudson.model.LabelFinder;
import hudson.model.TaskListener;
import hudson.slaves.ComputerListener;
import hudson.slaves.SlaveComputer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Lucie Votypkova
 */
@Extension
public class SlaveVariableLabelListener extends ComputerListener {

       @Override
    public void onOnline(Computer c, TaskListener taskListener) {
        if(c instanceof SlaveComputer){
            try{
            SlaveComputer slaveComputer = (SlaveComputer) c;
            String labels = slaveComputer.getEnvironment().get("JENKINS_SLAVE_LABELS");
            EnvironmentLabelsFinder finder = LabelFinder.all().get(EnvironmentLabelsFinder.class);
            if(labels!=null){
               finder.putLabels(c.getNode(), labels);
            }
            else{
                finder.putLabels(c.getNode(), "");
            }
            }
            catch (Exception e){
               Logger.getLogger(getClass().getName()).log(Level.WARNING, "", e);
            }
        }
      
    }
       
    @Override
     public void onConfigurationChange(){
        LabelFinder.all().get(EnvironmentLabelsFinder.class).updateComputers();
     }       
}
