using UnityEngine;
using System.Collections;

public class RemoveArm : MonoBehaviour {

    public Vector3 scale;

    // Use this for initialization
    void Start () {
	
	}

    void LateUpdate()
    {
        transform.localScale = scale;
    }

    // Update is called once per frame
    void Update () {
        
    }
}
