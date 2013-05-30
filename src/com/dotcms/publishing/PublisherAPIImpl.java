package com.dotcms.publishing;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dotmarketing.util.Logger;
import com.dotmarketing.util.PushPublishLogger;

public class PublisherAPIImpl implements PublisherAPI {



	@Override
	public PublishStatus publish(PublisherConfig config) throws DotPublishingException {

		return publish(config, new PublishStatus());
	}

	@Override
	public PublishStatus publish(PublisherConfig config, PublishStatus status) throws DotPublishingException {

		PushPublishLogger.log(this.getClass(), "Started Publishing Task", config.getId());

		try {

			List<Publisher> pubs = new ArrayList<Publisher>();
			List<Class> bundlers = new ArrayList<Class>();
			List<IBundler> confBundlers = new ArrayList<IBundler>();





			// init publishers
			for (Class<Publisher> c : config.getPublishers()) {
				Publisher p = c.newInstance();
				config = p.init(config);
				pubs.add(p);
				// get bundlers
				for (Class clazz : p.getBundlers()) {
					if (!bundlers.contains(clazz)) {
						bundlers.add(clazz);
					}
				}
			}

			if(config.isIncremental() && config.getEndDate()==null && config.getStartDate()==null) {
			    // if its incremental and start/end dates aren't se we take it from latest bundle
				if(BundlerUtil.bundleExists(config)){
					PublisherConfig p = BundlerUtil.readBundleXml(config);
					if(p.getEndDate() != null){
						config.setStartDate(p.getEndDate());
						config.setEndDate(new Date());
					}
					else{
					    config.setStartDate(null);
						config.setEndDate(new Date());

					}
				}
				else{
					config.setStartDate(null);
					config.setEndDate(new Date());
				}
			}


			// run bundlers

		File bundleRoot = BundlerUtil.getBundleRoot(config);
			BundlerUtil.writeBundleXML(config);
			for (Class<IBundler> c : bundlers) {
				IBundler b = (IBundler) c.newInstance();
				confBundlers.add(b);
				b.setConfig(config);
				BundlerStatus bs = new BundlerStatus(b.getClass().getName());
				status.addToBs(bs);
				PushPublishLogger.log(this.getClass(), "Running Bundler  : "+b.getName(), config.getId());
				b.generate(bundleRoot, bs);
				PushPublishLogger.log(this.getClass(), "Bundler Completed: "+b.getName(), config.getId());
			}
			config.setBundlers(confBundlers);

			// run publishers
			for (Publisher p : pubs) {
				PushPublishLogger.log(this.getClass(), "Running Publisher    : "+p.getClass().getName(), config.getId());
				p.process(status);
				PushPublishLogger.log(this.getClass(), "Publisher Completed  : "+p.getClass().getName(), config.getId());
			}

			PushPublishLogger.log(this.getClass(), "Completed Publishing Task", config.getId());
		} catch (Exception e) {
			Logger.error(PublisherAPIImpl.class, e.getMessage(), e);
			throw new DotPublishingException(e.getMessage(),e);
		}

		return status;
	}

}
