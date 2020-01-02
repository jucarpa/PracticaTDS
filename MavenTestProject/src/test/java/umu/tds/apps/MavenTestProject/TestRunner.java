package umu.tds.apps.MavenTestProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
  
public class TestRunner extends BlockJUnit4ClassRunner
{
    public TestRunner(Class<?> klass) throws InitializationError 
    {
        super(klass);
    }
      
    @Override
    protected List<FrameworkMethod> computeTestMethods() 
    {
        ArrayList<FrameworkMethod> result = new ArrayList<FrameworkMethod>();
        result.addAll(getTestClass().getAnnotatedMethods(OrderedTest.class));
        List<FrameworkMethod> testAnnotatedMethods = getTestClass().getAnnotatedMethods(Test.class);
        for(FrameworkMethod method : testAnnotatedMethods){
            if(!result.contains(method)){
                result.add( method );
            }
        }
          
        Collections.sort(result, new TestMethodOrderComparator());
        return result;
    }
}