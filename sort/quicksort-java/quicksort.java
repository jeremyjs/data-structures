import java.util.concurrent.ThreadLocalRandom;

class AssertException extends Exception {
  public AssertException () { }

  public AssertException (String message) {
    super(message);
  }
}

class QuickSort {
  private static final String LABEL = "[QuickSort]";

  private void swap_ip (int[] ary, int a, int b) {
    ary[a] ^= ary[b];
    ary[b] ^= ary[a];
    ary[a] ^= ary[b];
  }

  private void swap (int[] ary, int a, int b) {
    int tmp = ary[a];
    ary[a] = ary[b];
    ary[b] = tmp;
  }

  private int partition (int[] ary, int start, int end) {
    int p = start + ((end - start) / 2);
    int pivot = ary[p];
    int i = start;
    swap(ary, p, end);
    for (int j = start; j < end; j++) {
      if (ary[j] < pivot) {
        swap(ary, i, j);
        i++;
      }
    }
    swap(ary, i, end);
    return i;
  }
    
  private void qsort (int[] ary, int start, int end) {
    if (start >= end) return;
    int p = partition(ary, start, end);
    qsort(ary, start, p - 1);
    qsort(ary, p + 1, end);
  }

  public void sort (int[] ary) {
    qsort(ary, 0, ary.length - 1);
  }

  public int[] sorted (int[] input) {
    int[] ary = input.clone();
    sort(ary);
    return ary;
  }

  private static int randInt (int low, int high) {
    return ThreadLocalRandom.current().nextInt(low, high);
  }

  private static void labelPrintln (String s) {
    System.out.println(LABEL + " " + s);
  }

  private static void printArray (int[] ary) {
    System.out.print("[");
    for (int i = 0; i < ary.length - 1; i++) {
      System.out.print(ary[i] + ", ");
    }
    System.out.print(ary[ary.length-1] + "]");
  }

  private static void printlnArray (int[] ary) {
    printArray(ary);
    System.out.println();
  }

  private static void failAssertEqual (int[] ary1, int[] ary2) throws AssertException {
    System.out.println();
    System.out.println("ERROR");
    System.out.println("Expected:");
    printlnArray(ary1);
    System.out.println("Actual:");
    printlnArray(ary2);
    throw new AssertException();
  }

  private static void assertEqual (int[] ary1, int[] ary2) throws AssertException {
    if (ary1.length != ary2.length) {
      failAssertEqual(ary1, ary2);
    }
    for (int i = 0; i < ary1.length; i++) {
      if (ary1[i] != ary2[i]) {
        failAssertEqual(ary1, ary2);
      }
    }
    System.out.print(".");
  }

  public static void main (String args[]) {
    QuickSort qsort = new QuickSort();
    labelPrintln("Running tests...");

    try {
      assertEqual(
        new int[] { 1 },
        qsort.sorted(new int[] { 1 })
      );

      assertEqual(
        new int[] { 1, 2, 3 },
        qsort.sorted(new int[] { 3, 2, 1 })
      );

      assertEqual(
        new int[] { 1, 2, 3 },
        qsort.sorted(new int[] { 1, 2, 3 })
      );

      assertEqual(
        new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
        qsort.sorted(new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 })
      );

      assertEqual(
        new int[] { 1, 2, 2, 2, 5, 6, 7, 8, 9 },
        qsort.sorted(new int[] { 9, 2, 7, 2, 5, 6, 2, 1, 8 })
      );

      int m = 16;
      int[] big_ary = new int[Integer.MAX_VALUE / m];
      int[] sorted_big_ary = new int[Integer.MAX_VALUE / m];
      for (int i = 0; i < Integer.MAX_VALUE / m; i++) {
        big_ary[i] = ((Integer.MAX_VALUE / m) - i) - 1;
        sorted_big_ary[i] = i;
      }
      qsort.sort(big_ary);
      assertEqual(
        sorted_big_ary,
        big_ary
      );

      System.out.println();
      labelPrintln("PASS");
    }
    catch (AssertException e) {
      labelPrintln("FAIL");
    }
    catch (Exception e) {
      System.out.println();
      throw e;
    }
  }
}
