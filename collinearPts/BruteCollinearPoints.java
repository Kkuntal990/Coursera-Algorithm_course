


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;


    public BruteCollinearPoints(Point[] points) {

        checkArgs(points);

        ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();

        int n = points.length;

        Point[] copyOfPoints = Arrays.copyOf(points, n);

        Arrays.sort(copyOfPoints);


        for (int i = 0; i < n -3; i++) {
            Comparator<Point> comp = copyOfPoints[i].slopeOrder();
            for (int j = i +1; j < n-2; j++) {
                for (int k = j+ 1; k < n -1; k++) {
                    if (comp.compare(copyOfPoints[j], copyOfPoints[k]) != 0) {
                        continue;
                    }

                    for (int r = k+1; r < n; r++) {
                        if (comp.compare(copyOfPoints[j], copyOfPoints[r]) == 0) {
                            lineSegmentArrayList.add(new LineSegment(copyOfPoints[i], copyOfPoints[r]));
                        }
                    }

                }
            }
        }

        lineSegments = lineSegmentArrayList.toArray(new LineSegment[lineSegmentArrayList.size()]);
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

    private void checkArgs(Point[] points) {

        if (points == null) throw new IllegalArgumentException();

        for (int i = 0 ; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        for (int j = 0 ; j < points.length - 1; j++) {
            for (int k = j +1; k < points.length; k++) {
                if (points[j].compareTo(points[k]) == 0) throw new IllegalArgumentException();
            }
        }
    }


    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        Arrays.sort(points);
        for (int h = 0; h < points.length; h++) {
            StdOut.println(points[h].toString());
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();


        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }

