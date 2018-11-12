using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;

namespace LibarayManage
{
    public partial class AddAdminForm : Form
    {
        private MySqlConnection conn;
        /*
        * 连接数据库
        */
        public void ConnectMariaDB()
        {
            string connectionString = "server=localhost;user id=root;password=donky@16;database=libaraymanage";
            this.conn = new MySqlConnection(connectionString);
            conn.Open();
        }
        public AddAdminForm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ConnectMariaDB();
            string commandString = "insert into user values(null,'" + usernameText.Text + "','" + passwordText.Text + "','admin');";
            MySqlCommand command = new MySqlCommand(commandString, conn);
            command.ExecuteNonQuery();
            MessageBox.Show("添加用户成功！");
            conn.Close();
            Close();
        }
    }
}
