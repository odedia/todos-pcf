using RabbitMQ.Client;
using System.Net.Security;
using System.Security.Authentication;
using System.Text;
using System.Web.Mvc;

namespace RabbitMQ4.Controllers
{
    public class HomeController : Controller
    {
        IConnectionFactory _rabbitConnection;

        public HomeController(IConnectionFactory rabbitConnection)
        {
            _rabbitConnection = rabbitConnection;
            SslOption opt = (_rabbitConnection as ConnectionFactory).Ssl;
            if (opt != null && opt.Enabled)
            {
                opt.Version = SslProtocols.Tls | SslProtocols.Tls11 | SslProtocols.Tls12;

                // Only needed if want to disable certificate validations
                opt.AcceptablePolicyErrors = SslPolicyErrors.RemoteCertificateChainErrors |
                    SslPolicyErrors.RemoteCertificateNameMismatch | SslPolicyErrors.RemoteCertificateNotAvailable;
            }
        }

        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Receive()
        {
            string message = string.Empty;
            using (var connection = _rabbitConnection.CreateConnection())
            using (var channel = connection.CreateModel())
                
            {
                CreateQueue(channel);

                var data = channel.BasicGet("completedtasks", true);
                if (data != null)
                {
                    message = Encoding.UTF8.GetString(data.Body);
                }
            }

            return View((object)message);
        }

        protected void CreateQueue(IModel channel)
        {
            channel.QueueDeclare(queue: "completedtasks",
                             durable: false,
                             exclusive: false,
                             autoDelete: false,
                             arguments: null);

            channel.QueueBind("completedtasks", "completedtasks", "*", null);
        }
    }
}