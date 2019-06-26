/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private ArrayList<LineSegment> lineSegmentArrayList = new ArrayList<>();


    public FastCollinearPoints(Point[] points) {
        checkArgs(points);

        int n = points.length;

        Point[] pointsCopy = Arrays.copyOf(points, n);

        Arrays.sort(pointsCopy);



        for (Point head: points) {
            Arrays.sort(pointsCopy, head.slopeOrder());

            int j = 0;

            LinkedList<Point> pointsOnLine = new LinkedList<>();

            double prev = 0.0;

            for (Point point:pointsCopy) {
                double curr = head.slopeTo(point);
                if (j == 0 || point.slopeTo(head) != prev) {


                    if (pointsOnLine.size() >= 3) {
                        pointsOnLine.add(head);
                        Collections.sort(pointsOnLine);
                        if (head.compareTo(pointsOnLine.getFirst()) == 0) {
                            lineSegmentArrayList.add(new LineSegment(pointsOnLine.getFirst(),
                                                                     pointsOnLine.getLast()));
                        }


                    }
                    pointsOnLine.clear();
                }

                pointsOnLine.add(point);
                prev = point.slopeTo(head);
                j++;
            }

        }

        lineSegments = lineSegmentArrayList.toArray(new LineSegment[lineSegmentArrayList.size()]);


    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments()
    {
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
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
