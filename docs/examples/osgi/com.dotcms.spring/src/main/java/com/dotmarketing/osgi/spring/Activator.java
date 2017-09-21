package com.dotmarketing.osgi.spring;

import com.dotcms.repackage.org.apache.felix.http.api.ExtHttpService;
import com.dotcms.repackage.org.osgi.framework.BundleContext;
import com.dotcms.repackage.org.osgi.framework.ServiceReference;
import com.dotmarketing.filters.CMSFilter;
import com.dotmarketing.osgi.GenericBundleActivator;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by Jonathan Gamba
 * Date: 6/18/12
 */
public class Activator extends GenericBundleActivator {

    @SuppressWarnings ("unchecked")
    public void start ( BundleContext context ) throws Exception {

        //Initializing services...
        initializeServices( context );

        //Service reference to ExtHttpService that will allows to register servlets and filters
        ServiceReference sRef = context.getServiceReference( ExtHttpService.class.getName() );
        if ( sRef != null ) {

            //Publish bundle services
            publishBundleServices( context );

            ExtHttpService httpService = (ExtHttpService) context.getService( sRef );
            try {
                DispatcherServlet dispatcherServlet = new DispatcherServlet();
                dispatcherServlet.setContextConfigLocation( "spring/example-servlet.xml" );
                httpService.registerServlet( "/spring", dispatcherServlet, null, null );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            CMSFilter.addExclude( "/app/spring" );
        }
    }

    public void stop ( BundleContext context ) throws Exception {

        CMSFilter.removeExclude( "/app/spring" );

        //Unpublish bundle services
        unregisterServices( context );
    }

}