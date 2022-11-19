package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;


class MyListTest {

  @Test
  void test_simple_invocation_on_mock() {

    List<String> mockedList = mock(MyList.class);
    mockedList.size();
    verify(mockedList).size();
  }

  @Test
  void test_verify_number_of_interactions_with_mock() {

    List<String> mockedList = mock(MyList.class);
    mockedList.size();
    verify(mockedList, times(1)).size();
  }

  @Test
  void test_verify_no_interaction_with_whole_mock() {

    List<String> mockedList = mock(MyList.class);
    verifyNoInteractions(mockedList);
  }

  @Test
  void test_verify_no_interaction_with_specific_method() {

    List<String> mockedList = mock(MyList.class);
    verify(mockedList, times(0)).size();
  }

  // this should fail
  @Test
  void test_verify_no_unexpected_interactions() {

    List<String> mockedList = mock(MyList.class);
    mockedList.size();
    mockedList.clear();
    verify(mockedList).size();
    verifyNoMoreInteractions(mockedList);
  }

  @Test
  void test_verify_order_of_interactions() {

    List<String> mockedList = mock(MyList.class);
    mockedList.size();
    mockedList.add("a parameter");
    mockedList.clear();

    InOrder inOrder = Mockito.inOrder(mockedList);
    inOrder.verify(mockedList).size();
    inOrder.verify(mockedList).add("a parameter");
    inOrder.verify(mockedList).clear();
  }

  @Test
  void test_verify_an_interaction_has_not_occurred() {

    List<String> mockedList = mock(MyList.class);
    mockedList.size();
    verify(mockedList, never()).clear();
  }

  @Test
  void test_verify_an_interaction_has_occurred_at_least_certain_number_of_times() {

    List<String> mockedList = mock(MyList.class);
    mockedList.clear();
    mockedList.clear();
    mockedList.clear();

    verify(mockedList, atLeast(1)).clear();
    verify(mockedList, atMost(10)).clear();
  }

  @Test
  void test_verify_interaction_with_exact_argument() {

    List<String> mockedList = mock(MyList.class);
    mockedList.add("test");
    verify(mockedList).add("test");
  }

  @Test
  void test_verify_interaction_with_any_argument() {

    List<String> mockedList = mock(MyList.class);
    mockedList.add("test");
    verify(mockedList).add(anyString());
  }

  @Test
  void test_verify_interaction_using_argument_capture() {

    List<String> mockedList = mock(MyList.class);
    mockedList.addAll(Lists.<String>newArrayList("someElement"));

    ArgumentCaptor<List<String>> argumentCaptor = ArgumentCaptor.forClass(List.class);
    verify(mockedList).addAll(argumentCaptor.capture());

    List<String> capturedArgument = argumentCaptor.getValue();
    assertThat(capturedArgument).contains("someElement");
  }

}
