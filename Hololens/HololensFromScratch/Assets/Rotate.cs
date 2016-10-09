using UnityEngine;
using System.Collections;

public class Rotate : MonoBehaviour
{
    public float speed = 10f;
    public int count = 0;
    public string url = "http://vps75420.vps.ovh.ca:10110/ws/api/v1/getAction";
    public bool rotate = false;

    void Start ()
    {

        StartCoroutine(DoesWorkEveryX()); 
    }

    void Update()
    {
        if (rotate) transform.Rotate(Vector3.up, speed * Time.deltaTime);
    }

    IEnumerator DoesWorkEveryX()
    {
        int x = 0; 
        while (true)
        {
            WWW www = new WWW(url + "?p=" + (++x).ToString());
            yield return www;
         //   try
            {
                Debug.Log("Works");
           
                string data = www.text;
                Debug.Log("Retrieved text:" + data);
                if (data == "[\"single\"]")
                {
                    rotate = true;
                }
                else if (data == "[\"double\"]")
                {
                    rotate = false;
                }
            }
           // catch ( System.Exception ex)
           // {

           //     Debug.Log(ex.Message);
           // }
            yield return new WaitForSeconds(1f);
        }
    }
}
