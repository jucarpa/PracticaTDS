package umu.tds.apps.MavenTestProject;

import java.util.Comparator;
import org.junit.runners.model.FrameworkMethod;
  
public class TestMethodOrderComparator implements Comparator<FrameworkMethod> 
{
    @Override
    public int compare(FrameworkMethod method1, FrameworkMethod method2) 
    {
        OrderedTest annotation1 = method1.getAnnotation(OrderedTest.class);
        OrderedTest annotation2 = method2.getAnnotation(OrderedTest.class);
        if(annotation1 != null && annotation2 != null){
            return Integer.valueOf(annotation1.order()).compareTo(Integer.valueOf(annotation2.order()));
        }
        return 0;
    }
}