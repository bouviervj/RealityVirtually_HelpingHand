using UnityEngine;
using System.Collections;

public class ArmScript : MonoBehaviour {

    Animator anim;
    public int animate = 0;
    public string url = "http://vps75420.vps.ovh.ca:10110/ws/api/v1/getAction";
    public string url_sendevent = "http://vps75420.vps.ovh.ca:10110/ws/api/v1/triggerLaunch";

    public Vector3 offset;

    // Use this for initialization
    void Start () {
        anim = GetComponent<Animator>();
        StartCoroutine(DoesWorkEveryX());
    }
	
	// Update is called once per frame
	void Update () {

        anim.SetInteger("animation", animate);

    }

    void LateUpdate()
    {
        GameObject cameraRef = Camera.main.gameObject;
        transform.position = cameraRef.transform.position + offset;

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
                    animate = 1;
                }
                else if (data == "[\"double\"]")
                {
                    animate = 2;
                }
                else if (data == "[\"triple\"]")
                {
                    animate = 3;
                }
                else if (data == "[\"empty\"]")
                {
                    if (animate==3)
                    {
                        WWW www2 = new WWW(url_sendevent + "?p=" + (++x).ToString());
                        yield return www2;
                    }
                    animate = 0;
                }
            }
            // catch ( System.Exception ex)
            // {

            //     Debug.Log(ex.Message);
            // }
            yield return new WaitForSeconds(0.5f);
        }
    }

}
