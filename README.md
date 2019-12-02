# Phosor

Phosor is a simple, command line program written in Kotlin, that groups photos by creation date.

For each photo in given directory its creation date gets determined, catalog with creation date created, and photo moved to it.
It might be useful when you have a number of photos from your camera and would like to organize them by date.

## Prerequisites
Linux (not tested on other systems), JDK and Kotlin installed.

## Installing

1. Clone this repository into your disk.

2. Go to the phosor directory:
    ~~~bash
    $ cd /path/to/phosor/directory
    ~~~
   
3. Build a fat jar:
    ~~~bash
    phosor$ ./gradlew shadowJar
    ~~~
   
4. Copy fat jar to a location where you would like to keep it (ex. `/opt/phosor`), make it executable: 
    ~~~bash
    phosor# mkdir /opt/phosor
    phosor# cp ./build/libs/phosor-all.jar /opt/phosor/
    phosor# chmod +x /opt/phosor/phosor-all.jar
    ~~~

5. Create `sh` script for executing the jar, make it executable, in directory added to `$PATH` create link to the script:
    ~~~bash
    phosor# printf "#\!/bin/sh\njava -jar /opt/phosor/phosor-all.jar \$@\n" > /opt/phosor/phosor.sh
    phosor# chmod +x /opt/phosor/phosor.sh
    phosor# ln -s /opt/phosor/phosor.sh /usr/bin/phosor
    ~~~

7. Ensure that phosor works:
    ~~~bash
    $ phosor --version
    phosor version 0.1
    ~~~
   
## Usage
~~~bash
$ phosor --help
Usage: phosor [OPTIONS] [dir]

  Phosor is a command line tool that groups photos by creation date.

  For each photo in given directory its creation date gets determined, catalog
  with creation date created, and photo moved to it.

Options:
  --version           Show the version and exit
  -p, --pattern TEXT  Date pattern used to name catalogs, by default equals to
                      '[YYYY-MM-dd]' where 'yyyy' is year (ex. 2019), 'MM' is
                      month in year (ex. 12), 'dd' is day in month (ex. 02).
                      Pattern can contain only the following characters: '-',
                      '_', '.', '[', ']', 'G', 'y', 'Y', 'M', 'w', 'W', 'D',
                      'd', 'F', 'E', 'u', where: 'G' is Era designator (ex.
                      AD), 'y' is Year (ex. 1996; 96), 'Y' is Week year (ex.
                      2009; 09), 'M' is Month in year (ex. July; Jul; 07), 'w'
                      is Week in year (ex. 27), 'W' is Week in month (ex. 2),
                      'D' is Day in year (ex. 189), 'd' is Day in month (ex.
                      10), 'F' is Day of week in month (ex. 2), 'E' is Day
                      name in week (ex. Tuesday; Tue), 'u' is Day number of
                      week (1 = Monday, ..., 7 = Sunday)
  -v, --verbose       Enable verbose output
  -h, --help          Show this message and exit

Arguments:
  dir  Working directory, current directory by default
~~~

### Example

Consider you have number of photos in `/home/user/photos` directory and would like to move them to catalogs name 
like `YYYY.MM.dd`:
1. Check contents of `/home/user/photos`:
    ~~~bash
    $ ls /home/user/photos
   photo-taken-at-12-09-2019.jpg   photo-taken-at-30-07-2019.jpg
   photo-taken-at-28-08-2019.jpg
    ~~~

2. Run phosor:
    ~~~bash
    $ phosor --pattern "YYYY.MM.dd" /home/user/photos
    ~~~

3. Check results
    ~~~bash
    $ ls /home/user/photos
    2019.07.30  2019.08.26  2019.09.12
    $ ls /home/user/photos/2019.07.30
    photo-taken-at-30-07-2019.jpg
    $ ls /home/user/photos/2019.08.26
    photo-taken-at-26-08-2019.jpg
    $ ls /home/user/photos/2019.09.12
    photo-taken-at-12-09-2019.jpg
    ~~~