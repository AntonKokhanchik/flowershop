<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

        <!-- Popper JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="jumbotron">
            <div class="col-5">
                <h1> Flower </h1>
                <form action="/flower/new" method="post">
                    <div class="form-group">
                        <label for="title">Title</label>
                        <input class="form-control" id="title" name="title" />
                    </div>

                    <div class="form-group">
                       <label for="price">Price</label>
                       <input class="form-control" id="price" name="price" />
                    </div>

                    <div class="form-group">
                        <label for="count">Count</label>
                        <input class="form-control" id="count" name="count" />
                    </div>

                    <input class="btn btn-primary" type="submit" value="Save" />
                </form>
            </div>
        </div>
    </body>
</html>