package com.morev.movies;


import com.morev.movies.enumeration.Genre;
import com.morev.movies.enumeration.Role;
import com.morev.movies.model.Movie;
import com.morev.movies.model.User;
import com.morev.movies.repository.movie.MovieRepository;
import com.morev.movies.repository.user.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Seeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public Seeder(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) {
        seedUsers();
        seedMovie();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            User user1 = new User("Luxion Rob", "aidaynhi8@gmail.com", new BCryptPasswordEncoder().encode("Password123@"), Role.USER);
            User user2 = new User("User2", "user2@gmail.com", new BCryptPasswordEncoder().encode("Password123@"), Role.USER);
            User admin = new User("Admin", "admin@gmail.com", new BCryptPasswordEncoder().encode("Password123@"), Role.ADMIN);

            userRepository.saveAll(Arrays.asList(user1, user2, admin));
        }
    }

    private void seedMovie() {
        if (movieRepository.count() == 0) {
            Movie movie1 = new Movie(
                    new ObjectId().toHexString(),
                    "Avatar: The Way of Water remaster",
                    LocalDate.now(),
                    "https://www.youtube.com/watch?v=d9MyW72ELq0",
                    "https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
                    new ArrayList<>(Arrays.asList(Genre.SCIENCE_FICTION.getValue(), Genre.ACTION.getValue(), Genre.ADVENTURE.getValue())),
                    new ArrayList<>(Arrays.asList(

                            "https://image.tmdb.org/t/p/original/s16H6tpK2utvwDtzZ8Qy4qm5Emw.jpg",
                            "https://image.tmdb.org/t/p/original/evaFLqtswezLosllRZtJNMiO1UR.jpg",
                            "https://image.tmdb.org/t/p/original/198vrF8k7mfQ4FjDJsBmdQcaiyq.jpg",
                            "https://image.tmdb.org/t/p/original/zaapQ1zjKe2BGhhowh5pM251Gpl.jpg",
                            "https://image.tmdb.org/t/p/original/tQ91wWQJ2WRNDXwxuO7GCXX5VPC.jpg",
                            "https://image.tmdb.org/t/p/original/5gPQKfFJnl8d1edbkOzKONo4mnr.jpg",
                            "https://image.tmdb.org/t/p/original/2fS9cpar9rzxixwnRptg4bGmIym.jpg",
                            "https://image.tmdb.org/t/p/original/fkGR1ltNbvERk3topo4dP3gWsvR.jpg",
                            "https://image.tmdb.org/t/p/original/rb9IHprKNoSKqatP2vr25unUDSu.jpg",
                            "https://image.tmdb.org/t/p/original/37ZswIuRQcRBN7kHij5MBjzRMRt.jpg"
                    )), 2,
                    new ArrayList<>()
            );

            Movie movie2 = new Movie(
                    new ObjectId().toHexString(),
                    "M3GAN",
                    LocalDate.now(),
                    "https://www.youtube.com/watch?v=BRb4U99OU80",
                    "https://image.tmdb.org/t/p/w500/xBl5AGw7HXZcv1nNXPlzGgO4Cfo.jpg",
                    new ArrayList<>(Arrays.asList(Genre.SCIENCE_FICTION.getValue(), Genre.HORROR.getValue(), Genre.COMEDY.getValue())),
                    new ArrayList<>(Arrays.asList(

                            "https://image.tmdb.org/t/p/original/5kAGbi9MFAobQTVfK4kWPnIfnP0.jpg",
                            "https://image.tmdb.org/t/p/original/dlxzUj7z1MqEcFiwvvrj0bvBKDY.jpg",
                            "https://image.tmdb.org/t/p/original/q2fY4kMXKoGv4CQf310MCxpXlRI.jpg",
                            "https://image.tmdb.org/t/p/original/pTxwFdsdDWzpCRYuk1QbggdaOlL.jpg",
                            "https://image.tmdb.org/t/p/original/1zuz2RgFoOjulkjjNHHFc3WiHGB.jpg",
                            "https://image.tmdb.org/t/p/original/7HqxI1IXMloT9VTSuDC8ikaj810.jpg",
                            "https://image.tmdb.org/t/p/original/vpK2rp3J5LiC01HoNM0j9DEHQ1T.jpg",
                            "https://image.tmdb.org/t/p/original/cNHXdmr4amP6EPCMa0dqD8rwzDV.jpg",
                            "https://image.tmdb.org/t/p/original/txQLFd6rfQrskQhFENkS1jElptt.jpg",
                            "https://image.tmdb.org/t/p/original/dC4tj1ONdlZ2TWv4XD2SA1KUnJN.jpg"
                    )), 2,
                    new ArrayList<>()
            );

            Movie movie3 = new Movie(
                    new ObjectId().toHexString(),
                    "Troll",
                    LocalDate.now(),
                    "https://www.youtube.com/watch?v=AiohkY_XQYQ",
                    "https://image.tmdb.org/t/p/w500/9z4jRr43JdtU66P0iy8h18OyLql.jpg",
                    new ArrayList<>(Arrays.asList(Genre.FANTASY.getValue(), Genre.ACTION.getValue(), Genre.ADVENTURE.getValue())),
                    new ArrayList<>(Arrays.asList(

                            "https://image.tmdb.org/t/p/original/53BC9F2tpZnsGno2cLhzvGprDYS.jpg",
                            "https://image.tmdb.org/t/p/original/e9Qb2kmBnMXHCmNMI8NX1JbWhh1.jpg",
                            "https://image.tmdb.org/t/p/original/2WjOOOGUu6dp4r8VqR5n48DY7JG.jpg",
                            "https://image.tmdb.org/t/p/original/duIsyybgrC4S8kcCIVaxNOttV15.jpg",
                            "https://image.tmdb.org/t/p/original/3RS8runn9AfrYDzRVPWuGPmvXQf.jpg",
                            "https://image.tmdb.org/t/p/original/8wLRn2VvBlCu6cqYS4ypipnwosr.jpg",
                            "https://image.tmdb.org/t/p/original/zDqVVkmfvj47FBUE5lwE4rWnITu.jpg",
                            "https://image.tmdb.org/t/p/original/682Ui5DwZDdbIPzKAEOR7cJlMXa.jpg",
                            "https://image.tmdb.org/t/p/original/6jdlppcnGi3XuJamfs4Vl7HyxB.jpg",
                            "https://image.tmdb.org/t/p/original/uIq83ogs7QBEWi1aqmUrdDIH61m.jpg"
                    )), 2,
                    new ArrayList<>()
            );

            Movie movie4 = new Movie(
                    new ObjectId().toHexString(),
                    "Black Adam",
                    LocalDate.now(),
                    "https://www.youtube.com/watch?v=JaV7mmc9HGw",
                    "https://image.tmdb.org/t/p/w500/pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg",
                    new ArrayList<>(Arrays.asList(Genre.FANTASY.getValue(), Genre.ACTION.getValue(), Genre.SCIENCE_FICTION.getValue())),
                    new ArrayList<>(Arrays.asList(

                            "https://image.tmdb.org/t/p/original/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg",
                            "https://image.tmdb.org/t/p/original/9hNtTwY8P5v2MKnUeb7iuREI7Yb.jpg",
                            "https://image.tmdb.org/t/p/original/zplntIhzXyBiXFYWReETxh0uyFF.jpg",
                            "https://image.tmdb.org/t/p/original/yxkhM18dYwsRRffLnd9lz2d4i0v.jpg",
                            "https://image.tmdb.org/t/p/original/bgaBKREAfUtZgvd6zoV6RQRcIUt.jpg",
                            "https://image.tmdb.org/t/p/original/uqYxoj4hqwocwfBs2xxGyQT88Yk.jpg",
                            "https://image.tmdb.org/t/p/original/qBx97wytqlyPqXATHqRgIVFxJRU.jpg",
                            "https://image.tmdb.org/t/p/original/d6MhreFdMHONqX3iZlJGCF8UkIt.jpg",
                            "https://image.tmdb.org/t/p/original/9inNotReApz0n50WvWbrt0n1cbL.jpg",
                            "https://image.tmdb.org/t/p/original/pSOuqtJmdh7aI1yiK7M8e0PmbPC.jpg"
                    )), 2,
                    new ArrayList<>()
            );

            Movie movie5 = new Movie(
                    new ObjectId().toHexString(),
                    "Avatar",
                    LocalDate.now(),
                    "https://www.youtube.com/watch?v=5PSNL1qE6VY",
                    "https://image.tmdb.org/t/p/w500/jRXYjXNq0Cs2TcJjLkki24MLp7u.jpg",
                    new ArrayList<>(Arrays.asList(Genre.FANTASY.getValue(), Genre.ACTION.getValue(), Genre.SCIENCE_FICTION.getValue(), Genre.ADVENTURE.getValue())),
                    new ArrayList<>(Arrays.asList(

                            "https://image.tmdb.org/t/p/original/o0s4XsEDfDlvit5pDRKjzXR4pp2.jpg",
                            "https://image.tmdb.org/t/p/original/8I37NtDffNV7AZlDa7uDvvqhovU.jpg",
                            "https://image.tmdb.org/t/p/original/2YLOjUiczXEgVZFDSIeH3iWB3Ub.jpg",
                            "https://image.tmdb.org/t/p/original/Yc9q6QuWrMp9nuDm5R8ExNqbEq.jpg",
                            "https://image.tmdb.org/t/p/original/jlQJDD0L5ZojjlS0KYnApdO0n19.jpg",
                            "https://image.tmdb.org/t/p/original/sfw4m2tOgQRzhF6VXxaXGfd1vX.jpg",
                            "https://image.tmdb.org/t/p/original/7ABsaBkO1jA2psC8Hy4IDhkID4h.jpg",
                            "https://image.tmdb.org/t/p/original/xMMrBziwJqrgjerqpNeQvwuwiUp.jpg",
                            "https://image.tmdb.org/t/p/original/chauy3iJaZtrMbTr72rgNmOZwo3.jpg",
                            "https://image.tmdb.org/t/p/original/mYJkJ7YxJsUNI1nAOOUOpRN2auC.jpg"
                    )), 2,
                    new ArrayList<>()
            );

            Movie movie6 = new Movie(
                    new ObjectId().toHexString(),
                    "Roald Dahl's Matilda the Musical",
                    LocalDate.now(),
                    "https://www.youtube.com/watch?v=lroAhsDr2vI",
                    "https://image.tmdb.org/t/p/w500/ga8R3OiOMMgSvZ4cOj8x7prUNYZ.jpg",
                    new ArrayList<>(Arrays.asList(Genre.FANTASY.getValue(), Genre.FAMILY.getValue(), Genre.COMEDY.getValue())),
                    new ArrayList<>(Arrays.asList(

                            "https://image.tmdb.org/t/p/original/nWs0auTqn2UaFGfTKtUE5tlTeBu.jpg",
                            "https://image.tmdb.org/t/p/original/bPftMelR4N3jUg2LTlEblFz0gWk.jpg",
                            "https://image.tmdb.org/t/p/original/u2MLMkGEjJGQDs17Vmoej1RYFph.jpg",
                            "https://image.tmdb.org/t/p/original/jG52tsazn04F1fe8hPZfVv7ICKt.jpg",
                            "https://image.tmdb.org/t/p/original/4INEI7t7Vcg0cFvze7UIgwYCeSG.jpg",
                            "https://image.tmdb.org/t/p/original/krAu6znzW8c54NdJPneNi4bem1l.jpg",
                            "https://image.tmdb.org/t/p/original/6TUMppDMrYA4gzoaDUbbSnZFlxW.jpg",
                            "https://image.tmdb.org/t/p/original/hacV1h1SWrPlrerF3xpetvEdqT.jpg",
                            "https://image.tmdb.org/t/p/original/7iXsB1r9IK17ZFShqoxcHKQ7dLp.jpg",
                            "https://image.tmdb.org/t/p/original/dwiRYDLcFyDOkgkSc1JFtTr6Kdk.jpg"
                    )), 2,
                    new ArrayList<>()
            );

            Movie movie7 = new Movie(
                    new ObjectId().toHexString(),
                    "Black Panther: Wakanda Forever",
                    LocalDate.now(),
                    "https://www.youtube.com/watch?v=_Z3QKkl1WyM",
                    "https://image.tmdb.org/t/p/w500/cryEN3sWlgB2wTzcUNVtDGI8bkM.jpg",
                    new ArrayList<>(Arrays.asList(Genre.ACTION.getValue(), Genre.SCIENCE_FICTION.getValue(), Genre.ADVENTURE.getValue())),
                    new ArrayList<>(Arrays.asList(

                            "https://image.tmdb.org/t/p/original/yYrvN5WFeGYjJnRzhY0QXuo4Isw.jpg",
                            "https://image.tmdb.org/t/p/original/xDMIl84Qo5Tsu62c9DGWhmPI67A.jpg",
                            "https://image.tmdb.org/t/p/original/cs3LpA38BS2XDPfUzdgMB537XOo.jpg",
                            "https://image.tmdb.org/t/p/original/6SGMzCsaU094Mt32IHGkIYtIl06.jpg",
                            "https://image.tmdb.org/t/p/original/bty0TwJGsxMqYRptgyzn28Cxq5y.jpg",
                            "https://image.tmdb.org/t/p/original/h2jp3CSdTPc22mUqps9I8vXDPaN.jpg",
                            "https://image.tmdb.org/t/p/original/fSfWloWi5rmqbmC7GhO0HY2TMZW.jpg",
                            "https://image.tmdb.org/t/p/original/vZujZnmkYB5nGUC5d5llK9DbGLk.jpg",
                            "https://image.tmdb.org/t/p/original/8sMmAmN2x7mBiNKEX2o0aOTozEB.jpg",
                            "https://image.tmdb.org/t/p/original/geI3Mk7nehX1kvyIY3K5ajaiNfI.jpg"
                    )), 2,
                    new ArrayList<>()
            );

            Movie movie8 = new Movie(
                    new ObjectId().toHexString(),
                    "Strange World",
                    LocalDate.now(),
                    "https://www.youtube.com/watch?v=bKh2G73gCCs",
                    "https://image.tmdb.org/t/p/w500/kgJ8bX3zDQDM2Idkleis28XSVnu.jpg",
                    new ArrayList<>(Arrays.asList(Genre.FAMILY.getValue(), Genre.ADVENTURE.getValue(), Genre.SCIENCE_FICTION.getValue(), Genre.ANIMATION.getValue())),
                    new ArrayList<>(Arrays.asList(

                            "https://image.tmdb.org/t/p/original/5wDBVictj4wUYZ31gR5WzCM9dLD.jpg",
                            "https://image.tmdb.org/t/p/original/zNIlXd7CAz0hHAInbs2nsFRc0xQ.jpg",
                            "https://image.tmdb.org/t/p/original/1rukJHAP5p6DNHe75Oo1D0m3SnR.jpg",
                            "https://image.tmdb.org/t/p/original/aKbe411WyjTZy1OZUVIdNDYVf21.jpg",
                            "https://image.tmdb.org/t/p/original/9RKvxz0IryD2ofLYyGpnE7HeWlR.jpg",
                            "https://image.tmdb.org/t/p/original/kFURsDklj7QGMMkGJVwDBaJJn05.jpg",
                            "https://image.tmdb.org/t/p/original/v6oBDkd7ogXzTQxIU0H5SXq0hOL.jpg",
                            "https://image.tmdb.org/t/p/original/fBshLiEJcjdfrU3qQBIINcePSsm.jpg",
                            "https://image.tmdb.org/t/p/original/3oie0kID8SNCjkqN6Raweg5dJa.jpg",
                            "https://image.tmdb.org/t/p/original/zgFldVKON1Nxp8ui7HVABGKDQKM.jpg"
                    )), 2,
                    new ArrayList<>()
            );

            Movie movie9 = new Movie(
                    new ObjectId().toHexString(),
                    "The Woman King",
                    LocalDate.now(),
                    "https://www.youtube.com/watch?v=3RDaPV_rJ1Y",
                    "https://image.tmdb.org/t/p/w500/5O1GLla5vNuegqNxNhKL1OKE1lO.jpg",
                    new ArrayList<>(Arrays.asList(Genre.ACTION.getValue(), Genre.DRAMA.getValue(), Genre.HISTORY.getValue())),
                    new ArrayList<>(Arrays.asList(

                            "https://image.tmdb.org/t/p/original/gkseI3CUfQtMKX41XD4AxDzhQb7.jpg",
                            "https://image.tmdb.org/t/p/original/wSILunFEbvw00Ql2aaMHCSZf3cI.jpg",
                            "https://image.tmdb.org/t/p/original/xTsERrOCW15OIYl5aPX7Jbj38wu.jpg",
                            "https://image.tmdb.org/t/p/original/j06sSrtbqnZdSgG6yEduao95y48.jpg",
                            "https://image.tmdb.org/t/p/original/v4YV4ne1nwNni35iz4WmpZRZpCD.jpg",
                            "https://image.tmdb.org/t/p/original/6n5ln1vWGD3JyT6Ibt7ZxjSxY3v.jpg",
                            "https://image.tmdb.org/t/p/original/gi47WUUYVQWaLE5mJraS87ycdy6.jpg",
                            "https://image.tmdb.org/t/p/original/dTQOU5a32K3UPTIXHgipEqN41OM.jpg",
                            "https://image.tmdb.org/t/p/original/7zQJYV02yehWrQN6NjKsBorqUUS.jpg",
                            "https://image.tmdb.org/t/p/original/rdDL4y7BxGyXFEDJgAG4lz89bG2.jpg"
                    )), 2,
                    new ArrayList<>()
            );

            Movie movie10 = new Movie(
                    new ObjectId().toHexString(),
                    "Puss in Boots",
                    LocalDate.now(),
                    "",
                    "",
                    new ArrayList<>(Arrays.asList(Genre.ANIMATION.getValue(), Genre.ACTION.getValue(), Genre.ADVENTURE.getValue(), Genre.COMEDY.getValue(), Genre.FAMILY.getValue())),
                    new ArrayList<>(),
                    2,
                    new ArrayList<>()
            );
            movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9, movie10));
        }
    }
}
