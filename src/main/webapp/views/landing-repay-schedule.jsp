<!DOCTYPE html>
<html>
<head>
    <title>My Website</title>
    <style>

    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
    }

    header {
      background-color: #333;
      color: #fff;
      display: flex;
      justify-content: space-between;
      padding: 1rem;
    }

    header nav ul {
      display: flex;
      list-style: none;
      margin: 0;
      padding: 0;
    }

    header nav ul li {
      margin-right: 1rem;
    }

    header nav a {
      color: #fff;
      text-decoration: none;
    }

    main {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 3rem;
      text-align: center;
    }

    main h1 {
      font-size: 2.5rem;
      margin-bottom: 1rem;
    }

    main p {
      font-size: 1.2rem;
      margin-bottom: 2rem;
      max-width: 30rem;
    }

    main a.button {
      background-color: #333;
      border: none;
      color: #fff;
      padding: 0.5rem 1rem;
      text-decoration: none;
      margin-top: 2rem;
    }

    main a.button:hover {
      background-color: #444;
    }

    section {
      margin: 3rem;
      padding: 2rem;
      border: 1px solid #ddd;
    }

    section h2 {
      font-size: 2rem;
      margin-bottom: 1rem;
    }

    section ul {
      list-style: none;margin: 0;
      padding: 0;
    }

    section li {
      margin-bottom: 1rem;
    }

    footer {
      background-color: #333;
      color: #fff;
      display: flex;
      justify-content: center;
      padding: 1rem;
    }

    footer p {
      margin: 0;
    }

    </style>

    <script>
        function requestLodge(){
            document.getElementById('name').value=' ';
            document.getElementById('email').value=' ';
            document.getElementById('message').value=' ';

            alert("your Request has been lodged!");
        }
    </script>
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="#features">Validation check</a></li>
            <li><a href="#pricing">Customer registration</a></li>
            <li><a href="#contact">Contact US</a></li>
        </ul>
    </nav>
</header>
<main>
    <h1>Welcome to NeoBank!</h1>
    <p>We offer a wide range of loan products and services to help you achieve your goals. Learn more about our features and pricing, and get in touch with us today!</p>

</main>

</section>
<section id="features">
    <h2>RepaySchedule</h2>

    <a href="showRepayJsp" class="button">visit</a>
</section>



<section id="contact">
    <h2>Contact Us</h2>
    <p>Get in touch with us to learn more about our products and services. We're here to help!</p>
    <form>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email">
        <label for="message">Message:</label>
        <textarea id="message" name="message"></textarea>
        <button type="submit" onclick="requestLodge()" class="button">Send</button>
    </form>
</section>
<footer>
    <p>&copy; 2023 My Website</p>
</footer>
</body>
</html>