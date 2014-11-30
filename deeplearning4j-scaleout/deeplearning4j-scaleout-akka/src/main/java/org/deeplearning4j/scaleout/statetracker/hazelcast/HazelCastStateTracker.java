package org.deeplearning4j.scaleout.statetracker.hazelcast;


import org.deeplearning4j.scaleout.job.Job;
import org.deeplearning4j.scaleout.api.statetracker.IterateAndUpdate;
import org.deeplearning4j.scaleout.api.statetracker.UpdateSaver;
import org.deeplearning4j.scaleout.statetracker.updatesaver.LocalFileUpdateSaver;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * Tracks state of workers and jobs 
 * via hazelcast distributed data structures
 * @author Adam Gibson
 *
 */

@Path("/statetracker")
@Produces(MediaType.APPLICATION_JSON)
public class HazelCastStateTracker  extends BaseHazelCastStateTracker {

    public HazelCastStateTracker() throws Exception {
        setUpdateSaver(createUpdateSaver());
    }

    /**
     * Initializes the state tracker binding to the given port
     *
     * @param stateTrackerPort the port to bind to
     * @throws Exception
     */
    public HazelCastStateTracker(int stateTrackerPort) throws Exception {
        super(stateTrackerPort);
        setUpdateSaver(createUpdateSaver());

    }

    /**
     * Worker constructor
     *
     * @param connectionString
     */
    public HazelCastStateTracker(String connectionString) throws Exception {
        super(connectionString);
    }

    public HazelCastStateTracker(String connectionString, String type, int stateTrackerPort) throws Exception {
        super(connectionString, type, stateTrackerPort);
        setUpdateSaver(createUpdateSaver());

    }

    @Override
    public UpdateSaver createUpdateSaver() {
        return new LocalFileUpdateSaver(".",getH());
    }


    @Override
    public Job loadForWorker(String workerId) {
        return null;
    }

    @Override
    public void saveWorker(String workerId, Job d) {

    }

    /**
     * Updates  for mini batches
     *
     * @return the current list of updates for mini batches
     */
    @Override
    public IterateAndUpdate updates() {
        IterateAndUpdateImpl d2 = new IterateAndUpdateImpl(jobAggregator,updateSaver(),workers());
        return d2;
    }

}
