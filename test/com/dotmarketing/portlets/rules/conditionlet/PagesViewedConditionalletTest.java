package com.dotmarketing.portlets.rules.conditionlet;

import com.dotcms.repackage.org.junit.Assert;
//import com.dotcms.repackage.org.junit.Before;
//import com.dotcms.repackage.org.junit.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.dotcms.repackage.org.mockito.Mockito;
import com.dotmarketing.portlets.rules.model.ParameterModel;
import com.dotmarketing.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import com.dotcms.visitor.domain.Visitor;

import static com.dotmarketing.portlets.rules.parameter.comparison.Comparison.*;

/**
 * Created by freddy on 26/01/16.
 */
public class PagesViewedConditionalletTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession httpSessionMock;
    private PagesViewedConditionalet conditionlet = new PagesViewedConditionalet();

    @BeforeMethod
    public void before () {
        // Mock the request
        request = Mockito.mock(HttpServletRequest.class);

        // Mock the response
        response = Mockito.mock(HttpServletResponse.class);

        //Mock the session
        request.getSession().getAttribute(WebKeys.VISITOR);

        httpSessionMock = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(httpSessionMock);

        //Mock Visitor
        Visitor visitor = Mockito.mock(Visitor.class);
        Mockito.when(visitor.getNumberPagesViewed()).thenReturn(3);
        Mockito.when(httpSessionMock.getAttribute(WebKeys.VISITOR)).thenReturn(visitor);

    }

    @Test
    public void testEvaluateEquals() {
        Map<String, ParameterModel> parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, EQUAL.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "3"));

        PagesViewedConditionalet.Instance instance = conditionlet.instanceFrom(parameters);

        Assert.assertTrue(conditionlet.evaluate(request, response, instance));

        //---------------------------------------------------------------------------------------------------------

        parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, EQUAL.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "2"));

        instance = conditionlet.instanceFrom(parameters);

        Assert.assertFalse(conditionlet.evaluate(request, response, instance));
    }

    @Test
    public void testEvaluateNotEquals() {
        Map<String, ParameterModel> parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, NOT_EQUAL.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "2"));

        PagesViewedConditionalet.Instance instance = conditionlet.instanceFrom(parameters);

        Assert.assertTrue(conditionlet.evaluate(request, response, instance));

        //------------------------------------------------------------------------------------------------------------
        parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, NOT_EQUAL.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "3"));

        instance = conditionlet.instanceFrom(parameters);

        Assert.assertFalse(conditionlet.evaluate(request, response, instance));
    }

    @Test
    public void testEvaluateLessThan() {
        Map<String, ParameterModel> parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, LESS_THAN.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "2"));

        PagesViewedConditionalet.Instance instance = conditionlet.instanceFrom(parameters);

        Assert.assertTrue(conditionlet.evaluate(request, response, instance));

        //-------------------------------------------------------------------------------------------------------------
        parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, LESS_THAN.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "4"));

        instance = conditionlet.instanceFrom(parameters);

        Assert.assertFalse(conditionlet.evaluate(request, response, instance));
    }

    @Test
    public void testEvaluateGreaterThan() {
        Map<String, ParameterModel> parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, GREATER_THAN.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "5"));

        PagesViewedConditionalet.Instance instance = conditionlet.instanceFrom(parameters);

        Assert.assertTrue(conditionlet.evaluate(request, response, instance));

        //------------------------------------------------------------------------------------------------------------
        parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, GREATER_THAN.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "2"));

        instance = conditionlet.instanceFrom(parameters);

        Assert.assertFalse(conditionlet.evaluate(request, response, instance));
    }

    @Test
    public void testEvaluateLessThanOrEquals() {
        Map<String, ParameterModel> parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, LESS_THAN_OR_EQUAL.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "2"));

        PagesViewedConditionalet.Instance instance = conditionlet.instanceFrom(parameters);

        Assert.assertTrue(conditionlet.evaluate(request, response, instance));

        //------------------------------------------------------------------------------------------------------------
        parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, LESS_THAN_OR_EQUAL.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "3"));

        instance = conditionlet.instanceFrom(parameters);

        Assert.assertTrue(conditionlet.evaluate(request, response, instance));

        //------------------------------------------------------------------------------------------------------------
        parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, LESS_THAN_OR_EQUAL.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "4"));

        instance = conditionlet.instanceFrom(parameters);

        Assert.assertFalse(conditionlet.evaluate(request, response, instance));
    }

    @Test
    public void testEvaluateGreaterThanOrEquals() {
        Map<String, ParameterModel> parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, GREATER_THAN_OR_EQUAL.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "4"));

        PagesViewedConditionalet.Instance instance = conditionlet.instanceFrom(parameters);

        Assert.assertTrue(conditionlet.evaluate(request, response, instance));

        //------------------------------------------------------------------------------------------------------------
        parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, GREATER_THAN_OR_EQUAL.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "3"));

        instance = conditionlet.instanceFrom(parameters);

        Assert.assertTrue(conditionlet.evaluate(request, response, instance));

        //------------------------------------------------------------------------------------------------------------
        parameters = new HashMap<>();
        parameters.put(Conditionlet.COMPARISON_KEY, new ParameterModel(Conditionlet.COMPARISON_KEY, GREATER_THAN_OR_EQUAL.getId()));
        parameters.put(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY,
                new ParameterModel(PagesViewedConditionalet.NUMBER_PAGES_VIEWED_INPUT_KEY, "2"));

        instance = conditionlet.instanceFrom(parameters);

        Assert.assertFalse(conditionlet.evaluate(request, response, instance));
    }
}
